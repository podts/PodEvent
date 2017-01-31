package com.podts.podevent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A memory considerate implementation of HashEventListener.
 */
public class ConsiderateHashEventListener<E extends Event> implements EventListener<E> {
	
	protected Map<Class<? extends E>,Set<EventHandler<? extends E>>> handlers;
	protected Set<EventHandler<? extends E>> allHandlers;
	protected Set<EventHandler<? extends E>> safeHandlers;
	
	protected final Class<E> baseEventClass;
	
	@Override
	public Class<E> getMinimumEventClass() {
		return baseEventClass;
	}
	
	@Override
	public Collection<EventHandler<? extends E>> getEventHandlers() {
		if(handlers == null)
			return Collections.<EventHandler<? extends E>>emptyList();
		return safeHandlers;
	}

	@Override
	public boolean addEventHandler(EventHandler<? extends E> handler) {
		if(handlers == null) {
			handlers = new HashMap<Class<? extends E>,Set<EventHandler<? extends E>>>();
			allHandlers = new HashSet<EventHandler<? extends E>>();
			safeHandlers = Collections.unmodifiableSet(allHandlers);
		}
		else if(!allHandlers.add(handler)) return false;
		Class<? extends E> c = handler.getEventClass();
		Set<EventHandler<? extends E>> h = handlers.get(c);
		if(h == null) {
			h = new HashSet<EventHandler<? extends E>>();
			handlers.put(c, h);
		}
		h.add(handler);
		return true;
	}

	@Override
	public boolean removeEventHandler(EventHandler<? extends E> handler) {
		if(handlers == null) return false;
		if(!allHandlers.remove(handler)) {
			return false;
		}
		Class<?> c = handler.getEventClass();
		Set<EventHandler<? extends E>> h = handlers.get(c);
		if(h != null) {
			h.remove(handler);
			if(h.isEmpty()) handlers.remove(c);
		}
		return true;
	}

	@Override
	public void handleEvent(E e) {
		
		if(handlers == null || allHandlers.isEmpty()) return;
		
		for(Class<?> eventClass = e.getClass(); baseEventClass.isAssignableFrom(eventClass); eventClass = eventClass.getSuperclass()) {
			Set<EventHandler<? extends E>> h = handlers.get(eventClass);
			if(h == null) continue;
			for(EventHandler<? extends E> handler : h) {
				if(e.isCancelled() && !handler.ignoresCancelled()) continue;
				try {
					Method m = handler.getClass().getMethod("handle",handler.getEventClass());
					m.setAccessible(true);
					m.invoke(handler, e);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException| NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public void removeAllEventHandlers() {
		handlers = null;
		allHandlers = null;
		safeHandlers = null;
	}
	
	public ConsiderateHashEventListener(Class<E> c) {
		baseEventClass = c;
	}
	
}

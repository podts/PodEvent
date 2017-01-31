package com.podts.podevent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashEventListener<E extends Event> implements EventListener<E> {
	
	protected final Map<Class<? extends E>,Set<EventHandler<? extends E>>> handlers = new HashMap<Class<? extends E>,Set<EventHandler<? extends E>>>();
	protected final Set<EventHandler<? extends E>> allHandlers = new HashSet<EventHandler<? extends E>>();
	protected final Set<EventHandler<? extends E>> safeHandlers;
	
	protected final Class<E> baseEventClass;
	
	@Override
	public Class<E> getMinimumEventClass() {
		return baseEventClass;
	}
	
	@Override
	public Collection<EventHandler<? extends E>> getEventHandlers() {
		return safeHandlers;
	}

	@Override
	public boolean addEventHandler(EventHandler<? extends E> handler) {
		if(handler == null) return false;
		if(!allHandlers.add(handler)) return false;
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
		
		if(allHandlers.isEmpty()) return;
		
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
		allHandlers.clear();
		handlers.clear();
	}
	
	public HashEventListener(Class<E> c) {
		baseEventClass = c;
		safeHandlers = Collections.unmodifiableSet(allHandlers);
	}
	
}

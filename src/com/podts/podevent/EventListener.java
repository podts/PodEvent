package com.podts.podevent;

import java.util.Collection;

public interface EventListener<E extends Event> {
	
	public Collection<EventHandler<? extends E>> getEventHandlers();
	public boolean addEventHandler(EventHandler<? extends E> handler);
	public boolean removeEventHandler(EventHandler<? extends E> handler);
	
	public Class<E> getMinimumEventClass();
	
	public void handleEvent(E e);
	
}

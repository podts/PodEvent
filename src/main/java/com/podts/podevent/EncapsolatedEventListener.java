package com.podts.podevent;

import java.util.Collection;

public interface EncapsolatedEventListener<E extends Event> extends EventListener<E> {
	
	public EventListener<E> getEventListener();
	
	public default Collection<EventHandler<? extends E>> getEventHandlers() {
		return getEventListener().getEventHandlers();
	}
	
	public default boolean addEventHandler(EventHandler<? extends E> handler) {
		return getEventListener().addEventHandler(handler);
	}
	public default boolean removeEventHandler(EventHandler<? extends E> handler) {
		return getEventListener().removeEventHandler(handler);
	}
	
	public default void removeAllEventHandlers() {
		getEventListener().removeAllEventHandlers();
	}
	
	public default Class<E> getMinimumEventClass() {
		return getEventListener().getMinimumEventClass();
	}
	
	public default void handleEvent(E e) {
		getEventListener().handleEvent(e);
	}
	
}

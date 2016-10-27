package com.podts.podevent;

public abstract class SimpleEventHandler<E extends Event> implements EventHandler<E> {
	
	private final Class<E> eventClass;
	
	@Override
	public Class<E> getEventClass() {
		return eventClass;
	}
	
	public SimpleEventHandler(Class<E> eventClass) {
		this.eventClass = eventClass;
	}
	
}

package com.podts.podevent;

public interface EventHandler<E extends Event> {
	
	public Class<E> getEventClass();
	public void handle(E event);
	
}

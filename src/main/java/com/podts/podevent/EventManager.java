package com.podts.podevent;

/**
 * A interface of managing global event listeners as well as capability of firing events.
 */
public interface EventManager extends EventListener<Event> {
	
	public void callEvent(Event e);
	
	public default Class<Event> getMinimumEventClass() {
		return Event.class;
	}
	
}

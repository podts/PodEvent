package com.podts.podevent;

import java.util.Collection;

public class HashEventManager implements EventManager {
	
	protected final EventListener<Event> globalListener = new HashEventListener<Event>(Event.class);
	
	@Override
	public Collection<EventHandler<? extends Event>> getEventHandlers() {
		return globalListener.getEventHandlers();
	}

	@Override
	public boolean addEventHandler(EventHandler<? extends Event> handler) {
		return globalListener.addEventHandler(handler);
	}

	@Override
	public boolean removeEventHandler(EventHandler<? extends Event> handler) {
		return globalListener.removeEventHandler(handler);
	}
	
	@Override
	public void removeAllEventHandlers() {
		globalListener.removeAllEventHandlers();
	}
	
	/**
	 * Handles the event with only the global event handlers.
	 */
	@Override
	public void handleEvent(Event e) {
		globalListener.handleEvent(e);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void callEvent(Event e) {
		
		if(e instanceof SpecificEvent) {
			SpecificEvent sEvent = (SpecificEvent) e;
			sEvent.getSpecific().handleEvent(e);
		}
		
		handleEvent(e);
		
	}

}

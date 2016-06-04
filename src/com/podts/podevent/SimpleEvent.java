package com.podts.podevent;

public abstract class SimpleEvent implements Event {
	
	protected boolean cancelled = false;
	
	@Override
	public boolean cancelled() {
		return cancelled;
	}

	@Override
	public void cancel() {
		cancelled = true;
	}

}

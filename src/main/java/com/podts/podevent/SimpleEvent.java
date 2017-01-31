package com.podts.podevent;

public abstract class SimpleEvent implements Event {
	
	protected boolean cancelled = false;
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void cancel() {
		cancelled = true;
	}

}

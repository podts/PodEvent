package com.podts.podevent;

public enum EventPriority {
	
	HIGHEST(),
	HIGH(),
	MEDIUM(),
	LOW(),
	DEFAULT(false),
	MONITOR(false);
	
	private final boolean cancellable;
	
	public final boolean canCancell() {
		return cancellable;
	}
	
	private EventPriority() {
		cancellable = true;
	}
	
	private EventPriority(boolean c) {
		cancellable = c;
	}
	
}

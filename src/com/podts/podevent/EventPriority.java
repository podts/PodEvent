package com.podts.podevent;

public enum EventPriority {
	
	HIGHEST(),
	HIGH(),
	MEDIUM(),
	LOW(),
	DEFAULT(false),
	MONITOR(false);
	
	public final boolean cancellable;
	
	private EventPriority() {
		cancellable = true;
	}
	
	private EventPriority(boolean c) {
		cancellable = c;
	}
	
}

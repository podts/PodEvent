package com.podts.podevent;

public interface SpecificEvent<H extends EventListener<?>> extends Event {
	
	public H getSpecific();
	
}

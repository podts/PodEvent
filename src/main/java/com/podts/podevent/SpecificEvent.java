package com.podts.podevent;

import java.util.Set;

public interface SpecificEvent extends Event {
	
	@SuppressWarnings("rawtypes")
	public Set<? extends EventListener> getSpecific();
	
}

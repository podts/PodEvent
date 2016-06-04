package com.podts.podevent;

public abstract class SimpleSpecificEvent<T extends EventListener<?>> extends SimpleEvent implements SpecificEvent<T> {
	
	protected final T instance;
	
	@Override
	public T getSpecific() {
		return instance;
	}
	
	public SimpleSpecificEvent(T instance) {
		this.instance = instance;
	}
	
}

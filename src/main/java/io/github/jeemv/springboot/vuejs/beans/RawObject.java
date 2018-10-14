package io.github.jeemv.springboot.vuejs.beans;

public class RawObject {
	private Object value;

	public RawObject(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value+"";
	}
	
}

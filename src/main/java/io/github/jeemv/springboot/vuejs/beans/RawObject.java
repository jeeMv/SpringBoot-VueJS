package io.github.jeemv.springboot.vuejs.beans;


/**
 * RawObject
 * This class is part of spring-boot-vuejs
 * @author jc
 * @version 1.0.0
 *
 */
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

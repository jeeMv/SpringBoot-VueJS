package io.github.jeemv.springboot.vuejs.parts;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VuePart
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public abstract class VuePart {
	protected Map<String, Object> elements;
	public VuePart() {
		elements=new HashMap<>();
	}
	
	public void put(String key,Object value) {
		elements.put(key, value);
	}
	
	public boolean isEmpty() {
		return elements.size()==0;
	}

	@Override
	public String toString() {
		try {
			return JsUtils.objectToJSON(this.elements);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, Object> getElements() {
		return elements;
	}
	
}

package io.github.jeemv.springboot.vuejs.parts;

import io.github.jeemv.springboot.vuejs.configuration.VueConfig;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueMethods
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueMethods extends VuePart {
	
	public void add(String name,String body,String...params) {
		this.elements.put(name, new VueMethod(body, params));
	}
	public void addProperty(String name,Object value) {
		this.elements.put(name, value);
	}
	
	@Override
	public String toString() {
		String datas=super.toString();
		if(datas!=null) {
			if(VueConfig.debug) {
				datas=JsUtils.cleanJS(datas);
			}
			return datas;
		}
		return "";
	}
}

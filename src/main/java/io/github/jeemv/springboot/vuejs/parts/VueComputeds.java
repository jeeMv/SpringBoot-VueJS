package io.github.jeemv.springboot.vuejs.parts;

import io.github.jeemv.springboot.vuejs.configuration.VueConfig;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueComputeds
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueComputeds extends VuePart {
	
	public void add(String name,String get,String set) {
		this.elements.put(name, new VueComputed(get, set));
	}
	
	public void add(String name,String get) {
		this.add(name, get, null);
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

package io.github.jeemv.springboot.vuejs.parts;

import io.github.jeemv.springboot.vuejs.configuration.VueConfig;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

public class VueMethods extends VuePart {
	
	public void add(String name,String body,String...params) {
		this.elements.put(name, new VueMethod(body, params));
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

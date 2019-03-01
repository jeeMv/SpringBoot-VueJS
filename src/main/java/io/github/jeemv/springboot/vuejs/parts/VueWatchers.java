package io.github.jeemv.springboot.vuejs.parts;

import io.github.jeemv.springboot.vuejs.configuration.VueConfig;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

public class VueWatchers extends VuePart {
	
	public void add(String variableName,String methodName,boolean deep,boolean immediate) {
		VueWatcher vueWatcher=new VueWatcher(methodName);
		vueWatcher.setDeep(deep);
		vueWatcher.setImmediate(immediate);
		elements.put(variableName, vueWatcher);
	}
	
	public void add(String variableName,String methodName,boolean deep) {
		this.add(variableName,methodName,deep,false);
	}
	
	public VueWatcher addHandlers(String variableName,String...handlers) {
		VueWatcher vueWatcher=new VueWatcher(handlers);
		elements.put(variableName, vueWatcher);
		return vueWatcher;
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

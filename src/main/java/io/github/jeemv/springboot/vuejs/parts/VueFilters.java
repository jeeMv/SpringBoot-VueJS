package io.github.jeemv.springboot.vuejs.parts;


/**
 * VueFilters
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueFilters extends VuePart {
	
	public VueFilter add(String name,String body,String...args) {
		VueFilter filter=new VueFilter(name,body,args);
		elements.put(name,filter);
		return filter;
	}
	@Override
	public String toString() {
		String datas=super.toString();
		if(datas!=null) {
			return datas;
		}
		return "";
	}
}

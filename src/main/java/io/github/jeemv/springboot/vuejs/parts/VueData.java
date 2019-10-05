package io.github.jeemv.springboot.vuejs.parts;


/**
 * VueData
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueData extends VuePart {

	@Override
	public String toString() {
		String datas=super.toString();
		if(datas!=null) {
			return "function() { return "+datas+"; }";
		}
		return "";
	}
}

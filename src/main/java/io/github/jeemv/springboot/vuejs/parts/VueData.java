package io.github.jeemv.springboot.vuejs.parts;


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

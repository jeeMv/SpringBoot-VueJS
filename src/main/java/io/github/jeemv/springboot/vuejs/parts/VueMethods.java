package io.github.jeemv.springboot.vuejs.parts;


public class VueMethods extends VuePart {
	
	public void add(String name,String body,String...params) {
		this.elements.put(name, new VueMethod(body, params));
	}
	
	@Override
	public String toString() {
		String datas=super.toString();
		if(datas!=null) {
			return "{ "+datas+" }";
		}
		return "";
	}
}

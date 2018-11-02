package io.github.jeemv.springboot.vuejs.parts;


public class VueDirectives extends VuePart {
	public VueDirective add(String name) {
		VueDirective directive=new VueDirective();
		elements.put(name,directive);
		return directive;
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

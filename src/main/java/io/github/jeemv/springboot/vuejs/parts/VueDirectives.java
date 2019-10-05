package io.github.jeemv.springboot.vuejs.parts;


/**
 * VueDirectives
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueDirectives extends VuePart {
	public VueDirective add(String name) {
		VueDirective directive=new VueDirective();
		elements.put(name,directive);
		return directive;
	}
	
	public VueDirective add(String name,String shortHand) {
		VueDirective directive=new VueDirective();
		directive.setShortHand(shortHand);
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

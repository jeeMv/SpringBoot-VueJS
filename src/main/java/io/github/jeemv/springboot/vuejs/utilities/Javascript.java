package io.github.jeemv.springboot.vuejs.utilities;

/**
 * Javascript
 * Objects utilities
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.0
 *
 */
public class Javascript {
	public static String copy(String from,String to) {
		return "for(var k in "+to+") {"+to+"[k]="+from+"[k];}";
	}
	
	public static String clone(String from,String to,String initialize) {
		return to+"=Object.assign("+initialize+","+from+");";
	}
	
	public static String clone(String from,String to) {
		return clone(from,to,"{}");
	}
}

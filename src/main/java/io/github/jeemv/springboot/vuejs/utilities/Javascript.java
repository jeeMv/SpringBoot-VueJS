package io.github.jeemv.springboot.vuejs.utilities;

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

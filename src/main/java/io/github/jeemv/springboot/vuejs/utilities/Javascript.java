package io.github.jeemv.springboot.vuejs.utilities;

/**
 * Javascript
 * Objects utilities
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class Javascript {
	/**
	 * Copy all properties from one object to another
	 * @param from The object to copy from
	 * @param to The object to copy to
	 * @return
	 */
	public static String copy(String from,String to) {
		return "for(var k in "+to+") {"+to+"[k]="+from+"[k];}";
	}

	/**
	 * Clone an object
	 * @param from The object to clone
	 * @param to The object to clone to
	 * @param initialize The object to initialize the clone
	 * @return
	 */
	public static String clone(String from,String to,String initialize) {
		return to+"=Object.assign("+initialize+","+from+");";
	}

	/**
	 * Clone an object
	 * @param from The object to clone
	 * @param to The object to clone to
	 * @return
	 */
	public static String clone(String from,String to) {
		return clone(from,to,"{}");
	}

}

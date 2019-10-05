package io.github.jeemv.springboot.vuejs.parts;

/**
 * VueHook
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueHook {
	private String body;
	
	public VueHook(String body) {
		this.body=body;
	}
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "function(){"+body+"}";
	}
}

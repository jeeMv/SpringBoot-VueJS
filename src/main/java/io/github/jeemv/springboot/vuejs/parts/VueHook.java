package io.github.jeemv.springboot.vuejs.parts;

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

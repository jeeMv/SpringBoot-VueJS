package io.github.jeemv.springboot.vuejs.parts;

/**
 * VueComputed 
 * represents a computed property
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.0
 *
 */
public class VueComputed {
	private String get;
	private String set;
	
	public VueComputed(String get,String set) {
		this.get=get;
		this.set=set;
	}
	
	public VueComputed(String get) {
		this(get,null);
	}

	public String getGet() {
		return get;
	}

	public void setGet(String get) {
		this.get = get;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	@Override
	public String toString() {
		if(set==null) {
			return "function(){"+get+"}";
		}
		return "{get: function(){"+get+"}, set: function(v){"+set+"}}";
	}
}

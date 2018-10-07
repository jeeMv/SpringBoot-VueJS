package io.github.jeemv.springboot.vuejs;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.parts.VueComputeds;
import io.github.jeemv.springboot.vuejs.parts.VueData;
import io.github.jeemv.springboot.vuejs.parts.VueMethods;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueJS instance
 * @author jcheron
 * @version 1.0.0.0
 */
public class VueJS {
	private String el;
	private VueData data;
	private VueMethods methods;
	private VueComputeds computed;
	private String[] delimiters;
	
	/**
	 * @param element the DOM selector for the VueJS application
	 */
	public VueJS(String element) {
		this.el=element;
		data=new VueData();
		methods=new VueMethods();
		computed=new VueComputeds();
		this.setDelimiters("<%", "%>");
	}
	
	/**
	 * Defines the element delimiters (&lt;% %&gt;)
	 * @param start default: &lt;%
	 * @param end default: %&gt;
	 */
	public void setDelimiters(String start,String end) {
		delimiters= new String[] {start,end};
	}
	
	/**
	 * Adds a data
	 * @param key the name of the data
	 * @param value the value
	 */
	public void addData(String key,Object value) {
		data.put(key, value);
	}
	
	/**
	 * Adds a method
	 * @param name the method name
	 * @param body the method body (javascript)
	 */
	public void addMethod(String name,String body) {
		this.addMethod(name, body, new String[]{});
	}
	
	/**
	 * Adds a method with parameters
	 * @param name the method name
	 * @param body the method body (javascript)
	 * @param params the method parameters
	 */
	public void addMethod(String name,String body,String...params) {
		methods.add(name, body, params);
	}
	
	/**
	 * Adds an editable computed property
	 * @param name the name of the computed property
	 * @param get body of the getter
	 * @param set body of the setter
	 */
	public void addComputed(String name,String get,String set) {
		computed.add(name, get, set);
	}
	
	/**
	 * Adds a computed property
	 * @param name the name of the computed property
	 * @param get body of the getter
	 */
	public void addComputed(String name,String get) {
		this.addComputed(name, get, null);
	}
	
	/**
	 * @return the generated script (javascript)
	 */
	public String getScript() {
		try {
			String script="new Vue("+JsUtils.objectToJSON(this)+")";
			return "<script>"+script+"</script>";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getEl() {
		return el;
	}

	public VueData getData() {
		return data;
	}

	public VueMethods getMethods() {
		return methods;
	}

	public String[] getDelimiters() {
		return delimiters;
	}

	public VueComputeds getComputed() {
		return computed;
	}
}

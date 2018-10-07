package io.github.jeemv.springboot.vuejs;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.parts.VueComputeds;
import io.github.jeemv.springboot.vuejs.parts.VueData;
import io.github.jeemv.springboot.vuejs.parts.VueMethods;
import io.github.jeemv.springboot.vuejs.parts.VueWatcher;
import io.github.jeemv.springboot.vuejs.parts.VueWatchers;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueJS instance
 * @author jcheron
 * @version 1.0.0.1
 */
public class VueJS {
	private String el;
	private VueData data;
	private VueMethods methods;
	private VueComputeds computed;
	private VueWatchers watchers;
	private String[] delimiters;
	
	/**
	 * @param element the DOM selector for the VueJS application
	 */
	public VueJS(String element) {
		this.el=element;
		data=new VueData();
		methods=new VueMethods();
		computed=new VueComputeds();
		watchers=new VueWatchers();
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
	 * Attaches an existing function for observing the variable 
	 * The function must have the prototype function (oldVal, val)
	 * @param variableName the variable to observe
	 * @param methodName the method that observes
	 * @param deep if true, detect nested value changes inside Objects
	 * @param immediate if true, the callback will be called immediately after the start of the observation
	 */
	public void attachWatcher(String variableName,String methodName,boolean deep,boolean immediate) {
		watchers.add(variableName, methodName, deep, immediate);
	}
	
	/**
	 * Attaches an existing function for observing the variable
	 * The function must have the prototype function (oldVal, val)
	 * @param variableName the variable to observe
	 * @param methodName the method that observes
	 * @param deep if true, detect nested value changes inside Objects
	 */
	public void attachWatcher(String variableName,String methodName,boolean deep) {
		watchers.add(variableName, methodName, deep, false);
	}
	
	/**
	 * Attaches an existing function for observing the variable
	 * The function must have the prototype function (oldVal, val)
	 * @param variableName the variable to observe
	 * @param methodName the method that observes
	 */
	public void attachWatcher(String variableName,String methodName) {
		watchers.add(variableName, methodName, false, false);
	}
	
	/**
	 * Attaches an existing function for observing the variable, the callback will be called immediately after the start of the observation
	 * The function must have the prototype function (oldVal, val)
	 * @param variableName the variable to observe
	 * @param methodName the method that observes
	 */
	public void attachImmediateWatcher(String variableName,String methodName) {
		watchers.add(variableName, methodName, false, true);
	}
	
	/**
	 * Attaches an existing function for observing the variable, detect nested value changes inside Objects
	 * The function must have the prototype function (oldVal, val)
	 * @param variableName the variable to observe
	 * @param methodName the method that observes
	 */
	public void attachDeepWatcher(String variableName,String methodName) {
		watchers.add(variableName, methodName, true, false);
	}
	
	/**
	 * Adds a watcher on variableName
	 * Handlers have oldVal and val as parameters
	 * @param variableName the variable to observe
	 * @param deep if true, detect nested value changes inside Objects
	 * @param immediate if true, the callback will be called immediately after the start of the observation
	 * @param handlers the bodies of the methods that observe
	 */
	public void addWatcher(String variableName,boolean deep,boolean immediate,String...handlers) {
		VueWatcher vueWatcher=watchers.addHandlers(variableName, handlers);
		vueWatcher.setDeep(deep);
		vueWatcher.setImmediate(immediate);
	}
	
	/**
	 * Adds a watcher on variableName
	 * Handlers have oldVal and val as parameters
	 * @param variableName the variable to observe
	 * @param deep if true, detect nested value changes inside Objects
	 * @param handlers the bodies of the methods that observe
	 */
	public void addWatcher(String variableName,boolean deep,String...handlers) {
		this.addWatcher(variableName, deep, false, handlers);
	}
	
	/**
	 * Adds a watcher on variableName
	 * Handlers have oldVal and val as parameters
	 * @param variableName the variable to observe
	 * @param handlers the bodies of the methods that observe
	 */
	public void addWatcher(String variableName,String...handlers) {
		this.addWatcher(variableName, false, false, handlers);
	}
	
	/**
	 * Adds an immediate watcher on variableName, the callback will be called immediately after the start of the observation
	 * Handlers have oldVal and val as parameters
	 * @param variableName the variable to observe
	 * @param handlers the bodies of the methods that observe
	 */
	public void addImmediateWatcher(String variableName,String...handlers) {
		this.addWatcher(variableName, false, true, handlers);
	}
	
	/**
	 * Adds a deep watcher on variableName, detect nested value changes inside Objects
	 * Handlers have oldVal and val as parameters
	 * @param variableName the variable to observe
	 * @param handlers the bodies of the methods that observe
	 */
	public void addDeepWatcher(String variableName,String...handlers) {
		this.addWatcher(variableName, true, false, handlers);
	}
	
	/**
	 * Returns the generated script creating the VueJS instance
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

	@Override
	public String toString() {
		return getScript();
	}

	public void setEl(String el) {
		this.el = el;
	}

	public VueWatchers getWatchers() {
		return watchers;
	}
}

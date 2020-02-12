package io.github.jeemv.springboot.vuejs;

import java.util.HashMap;
import java.util.Map;

import io.github.jeemv.springboot.vuejs.beans.RawObject;
import io.github.jeemv.springboot.vuejs.parts.VueComputeds;
import io.github.jeemv.springboot.vuejs.parts.VueData;
import io.github.jeemv.springboot.vuejs.parts.VueDirective;
import io.github.jeemv.springboot.vuejs.parts.VueDirectives;
import io.github.jeemv.springboot.vuejs.parts.VueFilter;
import io.github.jeemv.springboot.vuejs.parts.VueFilters;
import io.github.jeemv.springboot.vuejs.parts.VueHook;
import io.github.jeemv.springboot.vuejs.parts.VueMethods;
import io.github.jeemv.springboot.vuejs.parts.VueWatcher;
import io.github.jeemv.springboot.vuejs.parts.VueWatchers;

/**
 * AbstractVueJS
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public abstract class AbstractVueJS {
	protected VueData data;
	protected VueMethods methods;
	protected VueComputeds computed;
	protected VueWatchers watchers;
	protected VueDirectives directives;
	protected VueFilters filters;
	protected Map<String, VueHook> hooks;
	
	public AbstractVueJS() {
		data=new VueData();
		methods=new VueMethods();
		computed=new VueComputeds();
		watchers=new VueWatchers();
		directives=new VueDirectives();
		filters=new VueFilters();
		hooks=new HashMap<>();
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
	 * Adds a data and the exact value without transformation
	 * @param key the name of the data
	 * @param value the value
	 */
	public void addDataRaw(String key,String value) {
		data.put(key, new RawObject(value));
	}
	
	/**
	 * Declare a data
	 * @param key the name of the data
	 */
	public void addData(String key) {
		data.put(key, "");
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
	
	protected void addHook(String name,String body) {
		hooks.put(name, new VueHook(body));
	}
	
	/**
	 * Adds a new directive in the vue instance
	 * @param name The directive name
	 * @return The created directive
	 */
	public VueDirective addDirective(String name) {
		return directives.add(name);
	}
	
	/**
	 * Adds a new directive in the vue instance
	 * @param name The directive name
	 * @param shortHand Shorthand function for bind and update
	 * @return The created directive
	 */
	public VueDirective addDirective(String name,String shortHand) {
		return directives.add(name,shortHand);
	}
	
	/**
	 * Adds a new filter in the vue instance
	 * @param name The filter name
	 * @param body The method body
	 * @param args The filter arguments
	 * @return The created filter
	 */
	public VueFilter addFilter(String name,String body,String...args) {
		return filters.add(name,body,args);
	}
	
	/**
	 * Adds code (body) for the beforeCreate hook
	 * @param body the code to execute
	 */
	public void onBeforeCreate(String body) {
		addHook("beforeCreate", body);
	}
	
	/**
	 * Adds code (body) for the created hook
	 * @param body the code to execute
	 */
	public void onCreated(String body) {
		addHook("created", body);
	}
	
	/**
	 * Adds code (body) for the beforeMount hook
	 * @param body the code to execute
	 */
	public void onBeforeMount(String body) {
		addHook("beforeMount", body);
	}
	
	/**
	 * Adds code (body) for the mounted hook
	 * @param body the code to execute
	 */
	public void onMounted(String body) {
		addHook("mounted", body);
	}
	
	/**
	 * Adds code (body) for the beforeUpdate hook
	 * @param body the code to execute
	 */
	public void onBeforeUpdate(String body) {
		addHook("beforeUpdate", body);
	}
	
	/**
	 * Adds code (body) for the updated hook
	 * @param body the code to execute
	 */
	public void onUpdated(String body) {
		addHook("updated", body);
	}
	
	/**
	 * Adds code (body) for the updated hook
	 * wait until the entire view has been re-rendered with $nextTick
	 * @param body the code to execute
	 */
	public void onUpdatedNextTick(String body) {
		addHook("updated", "this.$nextTick(function () {"+body+"})");
	}
	
	/**
	 * Adds code (body) for the beforeDestroy hook
	 * @param body the code to execute
	 */
	public void onBeforeDestroy(String body) {
		addHook("beforeDestroy", body);
	}
	
	/**
	 * Adds code (body) for the destroyed hook
	 * @param body the code to execute
	 */
	public void onDestroyed(String body) {
		addHook("destroyed", body);
	}
	
	public Map<String, VueHook> getHooks() {
		return hooks;
	}

	/**
	 * Returns the generated script creating the instance
	 * @return the generated script (javascript)
	 */
	public abstract String getScript();

	public VueData getData() {
		return data;
	}

	public VueMethods getMethods() {
		return methods;
	}

	public VueComputeds getComputed() {
		return computed;
	}

	public VueDirectives getDirectives() {
		return directives;
	}

	@Override
	public String toString() {
		return getScript();
	}

	public VueWatchers getWatchers() {
		return watchers;
	}
	
	
	public VueFilters getFilters() {
		return filters;
	}
	
}

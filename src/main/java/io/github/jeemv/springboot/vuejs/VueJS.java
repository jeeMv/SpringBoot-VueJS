package io.github.jeemv.springboot.vuejs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.components.VueComponent;
import io.github.jeemv.springboot.vuejs.parts.AbstractVueComposition;
import io.github.jeemv.springboot.vuejs.parts.VueDirective;
import io.github.jeemv.springboot.vuejs.parts.VueFilter;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;


/**
 * VueJS instance
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.3
 *
 */
public class VueJS extends AbstractVueJS{
	protected String el;
	protected String[] delimiters;
	protected Map<String,VueComponent> globalComponents;
	protected Map<String,AbstractVueComposition> globalElements;
	
	/**
	 * @param element the DOM selector for the VueJS application
	 */
	public VueJS(String element) {
		super();
		this.el=element;
		this.setDelimiters("<%", "%>");
		globalComponents=new HashMap<>();
		globalElements=new HashMap<>();
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
	 * Returns the generated script creating the VueJS instance
	 * @return the generated script (javascript)
	 */
	@Override
	public String getScript() {
		String script="";
		try {
			for(Entry<String, VueComponent> entry:globalComponents.entrySet()) {
				script+=entry.getValue();
			}
			for(Entry<String, AbstractVueComposition> entry:globalElements.entrySet()) {
				script+=entry.getValue().getScript();
			}
			script+="new Vue("+JsUtils.objectToJSON(this)+");";
			return "<script>"+script+"</script>";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return script;
	}
	
	/**
	 * Adds a new global component
	 * @param name The component name
	 * @return The created component
	 */
	public VueComponent addGlobalComponent(String name) {
		VueComponent component= new VueComponent(name);
		globalComponents.put(name,component);
		return component;
	}
	
	/**
	 * Adds a new global directive
	 * @param name The directive name
	 * @return The created directive
	 */
	public VueDirective addGlobalDirective(String name) {
		VueDirective directive=new VueDirective(name);
		globalElements.put(name, directive);
		return directive;
	}
	
	/**
	 * Adds a new global filter
	 * @param name The filter name
	 * @param body The filter body
	 * @param args The filter arguments
	 * @return The created filter
	 */
	public VueFilter addGlobalFilter(String name,String body,String...args) {
		VueFilter filter=new VueFilter(name,body,args);
		globalElements.put(name, filter);
		return filter;
	}
	
	public String getEl() {
		return el;
	}

	public String[] getDelimiters() {
		return delimiters;
	}


	public void setEl(String el) {
		this.el = el;
	}

}

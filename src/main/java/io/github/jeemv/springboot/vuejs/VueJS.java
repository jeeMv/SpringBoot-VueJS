package io.github.jeemv.springboot.vuejs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.components.VueComponent;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueJS instance
 * @author jcheron
 * @version 1.0.0.2
 */
public class VueJS extends AbstractVueJS{
	protected String el;
	protected String[] delimiters;
	protected Map<String,VueComponent> globalComponents;
	
	/**
	 * @param element the DOM selector for the VueJS application
	 */
	public VueJS(String element) {
		super();
		this.el=element;
		this.setDelimiters("<%", "%>");
		globalComponents=new HashMap<>();
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
			script+="new Vue("+JsUtils.objectToJSON(this)+");";
			return "<script>"+script+"</script>";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return script;
	}
	
	public VueComponent addGlobalComponent(String name) {
		VueComponent component= new VueComponent(name);
		globalComponents.put(name,component);
		return component;
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

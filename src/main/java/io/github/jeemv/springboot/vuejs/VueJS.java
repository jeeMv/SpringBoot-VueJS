package io.github.jeemv.springboot.vuejs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.components.VueComponent;
import io.github.jeemv.springboot.vuejs.configuration.VueJSAutoConfiguration;
import io.github.jeemv.springboot.vuejs.configuration.VueJSProperties;
import io.github.jeemv.springboot.vuejs.parts.AbstractVueComposition;
import io.github.jeemv.springboot.vuejs.parts.VueDirective;
import io.github.jeemv.springboot.vuejs.parts.VueFilter;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueJS instance This class is part of springBoot-VueJS
 * 
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.4
 *
 */
@Component
public class VueJS extends AbstractVueJS {
	protected String el;
	protected String[] delimiters;
	protected boolean useAxios;
	protected boolean vuetify;
	protected Map<String, VueComponent> globalComponents;
	protected Map<String, AbstractVueComposition> globalElements;

	@Autowired(required = false)
	protected VueJSAutoConfiguration vueJSAutoConfiguration;
	private final Logger logger;

	@PostConstruct
	public void init() {
		if (null == vueJSAutoConfiguration) {
			logger.debug("VueJS configuration not yet loaded");
		} else {
			VueJSProperties vueJSProperties = vueJSAutoConfiguration.getVueJSProperties();
			setDelimiters(vueJSProperties.getPrefix(), vueJSProperties.getPostfix());
			if (vueJSProperties.isUseAxios()) {
				useAxios = true;
			}
			if (vueJSProperties.isVuetify()) {
				vuetify = true;
			}
			el = vueJSProperties.getEl();
		}
	}

	/**
	 * @param element the DOM selector for the VueJS application
	 */
	public VueJS(String element) {
		super();
		this.el = element;
		globalComponents = new HashMap<>();
		globalElements = new HashMap<>();
		logger = LoggerFactory.getLogger(VueJS.class);
	}

	/**
	 * Starts the VueJS app with v-app element
	 */
	public VueJS() {
		this("v-app");
	}

	/**
	 * Defines the element delimiters (&lt;% %&gt;)
	 * 
	 * @param start default: &lt;%
	 * @param end   default: %&gt;
	 */
	public void setDelimiters(String start, String end) {
		delimiters = new String[] { start, end };
	}

	/**
	 * Returns the generated script creating the VueJS instance
	 * 
	 * @return the generated script (javascript)
	 */
	@Override
	public String getScript() {
		StringBuilder script = new StringBuilder();
		if (useAxios) {
			script = new StringBuilder("Vue.prototype.$http = axios;\n");
		}
		try {
			for (Entry<String, VueComponent> entry : globalComponents.entrySet()) {
				script.append(entry.getValue());
			}
			for (Entry<String, AbstractVueComposition> entry : globalElements.entrySet()) {
				script.append(entry.getValue().getScript());
			}
			script.append("new Vue(").append(JsUtils.objectToJSON(this)).append(");");
			return "<script>" + script + "</script>";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return script.toString();
	}

	/**
	 * Adds a new global component
	 * 
	 * @param name The component name
	 * @return The created component
	 */
	public VueComponent addGlobalComponent(String name) {
		VueComponent component = new VueComponent(name);
		globalComponents.put(name, component);
		return component;
	}

	/**
	 * Adds a new global directive
	 * 
	 * @param name The directive name
	 * @return The created directive
	 */
	public VueDirective addGlobalDirective(String name) {
		VueDirective directive = new VueDirective(name);
		globalElements.put(name, directive);
		return directive;
	}

	/**
	 * Adds a new global filter
	 * 
	 * @param name The filter name
	 * @param body The filter body
	 * @param args The filter arguments
	 * @return The created filter
	 */
	public VueFilter addGlobalFilter(String name, String body, String... args) {
		VueFilter filter = new VueFilter(name, body, args);
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

	/**
	 * Sets axios as library to use for $http do not forget to include the
	 * corresponding js file
	 * 
	 * @param useAxios
	 */
	public void setUseAxios(boolean useAxios) {
		this.useAxios = useAxios;
	}

	public boolean isVuetify() {
		return vuetify;
	}

}

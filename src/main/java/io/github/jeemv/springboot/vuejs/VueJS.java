package io.github.jeemv.springboot.vuejs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.jeemv.springboot.vuejs.configuration.VueVersion;
import io.github.jeemv.springboot.vuejs.configuration.versions.Vue2Version;
import io.github.jeemv.springboot.vuejs.configuration.versions.Vue3Version;
import io.github.jeemv.springboot.vuejs.utilities.Http;
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
 * @version 1.0.5
 *
 */
@Component
public class VueJS extends AbstractVueJS {
	protected String el;
	protected String[] delimiters;
	protected boolean useAxios;
	protected boolean vuetify;

	protected String vueVersion;

	@JsonIgnore
	protected String beforeMountScript;
	protected Map<String, VueComponent> globalComponents;
	protected Map<String, AbstractVueComposition> globalElements;

	@Autowired(required = false)
	protected VueJSAutoConfiguration vueJSAutoConfiguration;
	private final Logger logger;

	protected VueVersion getVueVersion(String globalScript) {
		if (isVue2()) {
			return new Vue2Version(useAxios,globalScript+this.beforeMountScript);
		}
		return new Vue3Version(useAxios,globalScript+this.beforeMountScript);
	}
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
			Http.RESPONSE_DATA = vueJSProperties.getHttpData();
			el = vueJSProperties.getEl();
			vueVersion = vueJSProperties.getVueVersion();
		}
	}

	/**
	 * @param element the DOM selector for the VueJS application.
	 */
	public VueJS(String element) {
		super();
		this.el = element;
		globalComponents = new HashMap<>();
		globalElements = new HashMap<>();
		vueVersion=VueVersion.VUE_3;
		beforeMountScript="";
		logger = LoggerFactory.getLogger(VueJS.class);
	}

	/**
	 * Starts the VueJS app with v-app element.
	 */
	public VueJS() {
		this("v-app");
	}

	/**
	 * Defines the element delimiters (&lt;% %&gt;).
	 * 
	 * @param start default: &lt;%
	 * @param end   default: %&gt;
	 */
	public void setDelimiters(String start, String end) {
		delimiters = new String[] { start, end };
	}

	/**
	 * Returns the generated script creating the VueJS instance.
	 * 
	 * @return the generated script (javascript)
	 */
	@Override
	public String getScript() {
		StringBuilder globalScript = new StringBuilder();
		StringBuilder script = new StringBuilder();

		try {
			for (Entry<String, VueComponent> entry : globalComponents.entrySet()) {
				globalScript.append(entry.getValue());
			}
			for (Entry<String, AbstractVueComposition> entry : globalElements.entrySet()) {
				globalScript.append(entry.getValue().getScript());
			}
			VueVersion vueVersion = getVueVersion(globalScript.toString());
			script.append(vueVersion.generateVueJSInstance(JsUtils.objectToJSON(this),el));
			return "<script>" + script + "</script>";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return script.toString();
	}

	/**
	 * Adds a new global component.
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
	 * Adds a new global directive.
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
	 * Adds a new global filter.
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
	 * corresponding js file.
	 * 
	 * @param useAxios
	 */
	public void setUseAxios(boolean useAxios) {
		this.useAxios = useAxios;
	}

	public boolean isVuetify() {
		return vuetify;
	}

	public boolean isVue2() {
		return vueVersion.startsWith("2");
	}

	/**
	 * Adds a script to be executed before the VueJS instance is mounted.
	 * @param script
	 */
	public void addBeforeMountScript(String script) {
		this.beforeMountScript+=script;
	}

	/**
	 * Loads a script from a file and adds it to the before mount script.
	 * @param filename
	 * @throws IOException
	 */
	public void loadAndAddBeforeMountScript(String filename) throws IOException {
		this.beforeMountScript+=JsUtils.loadJsFile(filename);
	}

}

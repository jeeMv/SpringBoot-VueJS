package io.github.jeemv.springboot.vuejs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * VueJSProperties
 * This class is part of springBoot-VueJS
 * @author jcheron
 * @version 1.0.2
 *
 */
@ConfigurationProperties(prefix = "springboot.vuejs")
@Component
public class VueJSProperties {
	public static final String DEFAULT_PREFIX = "<%";

	public static final String DEFAULT_POSTFIX = "%>";
	
	public static final String DEFAULT_ELEMENT = "v-app";
	
    private String[] delimiters;
    private boolean axios;
    private String el;
    
    private boolean vuetify;

	private String vueVersion="3.*";

	private String httpData="response.data";

	public String[] getDelimiters() {
		return delimiters;
	}
	
	public String getPrefix() {
		if(delimiters!=null && delimiters.length>0) {
			return delimiters[0];
		}
		return DEFAULT_PREFIX;
	}
	
	public String getPostfix() {
		if(delimiters!=null && delimiters.length>1) {
			return delimiters[1];
		}
		return DEFAULT_POSTFIX;
	}
	
	public void setEl(String el) {
		this.el=el;
	}
	
	public String getEl() {
		if(el!=null) {
			return el;
		}
		return DEFAULT_ELEMENT;
	}

	public String getVueVersion() {
		return vueVersion;
	}

	public void setDelimiters(String... delimiters) {
		this.delimiters = delimiters;
	}

	public boolean isUseAxios() {
		return axios;
	}
	
	public boolean getAxios() {
		return axios;
	}

	public void setAxios(boolean axios) {
		this.axios = axios;
	}

	public String getHttpData() {
		return httpData;
	}

	public void setHttpData(String httpData) {
		this.httpData=httpData;
	}

	public boolean isVuetify() {
		return vuetify;
	}

	public void setVuetify(boolean vuetify) {
		this.vuetify = vuetify;
	}

	public void setVueVersion(String vueVersion) {
		this.vueVersion=vueVersion;
	}
}

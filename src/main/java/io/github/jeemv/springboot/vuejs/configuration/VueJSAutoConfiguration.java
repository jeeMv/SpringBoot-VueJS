package io.github.jeemv.springboot.vuejs.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.github.jeemv.springboot.vuejs.VueJS;

@Component
@Configuration
@ConditionalOnClass(VueJS.class)
@EnableConfigurationProperties(VueJSProperties.class)
public class VueJSAutoConfiguration {
	
	private final VueJSProperties vueJSProperties;

	private final ApplicationContext applicationContext;

	public VueJSAutoConfiguration(VueJSProperties vueJSProperties,
			ApplicationContext applicationContext) {
		this.vueJSProperties = vueJSProperties;
		this.applicationContext = applicationContext;
	}
	
	public VueJSProperties getVueJSProperties() {
		return vueJSProperties;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}

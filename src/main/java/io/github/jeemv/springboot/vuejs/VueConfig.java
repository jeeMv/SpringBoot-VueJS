package io.github.jeemv.springboot.vuejs;

import java.util.Properties;

public class VueConfig{
	private static VueConfig instance;
	public static boolean debug=false;
	private Properties properties;
	private VueConfig() {
		properties=new Properties();
	}
	private static VueConfig getInstance() {
		if(instance==null) {
			instance=new VueConfig();
		}
		return instance;
	}
	public static Properties getProperties() {
		return getInstance().properties;
	}
	public static String getTemplateComponentFolder() {	
		return getProperties().getProperty("template-component-folder","vueJS");
	}
	
	public static String getComponentFolder() {	
		return getProperties().getProperty("component-folder","vueJS");
	}
}

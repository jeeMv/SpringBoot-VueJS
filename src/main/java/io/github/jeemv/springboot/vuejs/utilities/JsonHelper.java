package io.github.jeemv.springboot.vuejs.utilities;


/**
 * JsonHelper
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.0
 *
 */
public class JsonHelper {
	private String[] keys;
	private String[] values;
	private int pos;
	private String quote="'";
	
	public JsonHelper(String...keys) {
		setKeys(keys);
	}
	
	private String quoteValue(String value) {
		return quote+value+quote;
	}
	public JsonHelper setKeys(String... keys) {
		this.keys=keys;
		return this;
	}
	
	public JsonHelper setValues(String... values) {
		this.values=values;
		return this;
	}
	
	/**
	 * @return a JSON array
	 */
	public String asArray() {
		StringBuilder result=new StringBuilder();
		result.append('[');
		int limit=values.length;
		String prefix="";
		while(pos<limit) {
			result.append(prefix+asObject());
			prefix=",";
		}
		result.append(']');
		return result.toString();
	}
	
	/**
	 * @return a JSON object
	 */
	public String asObject() {
		StringBuilder result=new StringBuilder();
		result.append('{');
		int i;
		int nb=keys.length;
		int limit=values.length;
		String prefix="";
		for(i=0;i<nb && i+pos<limit;i++) {
			result.append(prefix+keys[i]+":"+quoteValue(values[i+pos]));
			prefix=",";
		}
		result.delete(result.length() - 1, result.length());
		pos+=i;
		result.append('}');
		return result.toString();
	}
	
}

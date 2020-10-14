package io.github.jeemv.springboot.vuejs.utilities.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JsResourceElement
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.0
 *
 */
public class JsResourceElement {
	private String jsContent;
	private Map<String, Object> variables;
	public JsResourceElement() {
		this(new HashMap<>());
	}

	public JsResourceElement(Map<String, Object> variables) {
		this.variables = variables;
	}

	/**
	 * Adds java content identified by its name.
	 * 
	 * @param name
	 *            The name of the java content (use ${name} in the js file to
	 *            refer to it.
	 * @param value
	 *            The java content
	 * @return the previous value associated with name, or null if there was no
	 *         mapping for name.
	 */
	public Object put(String name, Object value) {
		return variables.put(name, value);
	}

	/**
	 * Adds an array of names,values to the resource.
	 * 
	 * @param keyValues
	 *            Sample new Object[][] { {"id",5},{"name","doe"} }
	 */
	public void addVariables(Object[][] keyValues) {
		for (Object[] array : keyValues) {
			if (array.length == 2) {
				variables.put(array[0] + "", array[1]);
			}
		}
	}

	/**
	 * Adds names,values of contents to the resource from a json array.
	 * 
	 * @param jsonString
	 *            sample "{'id':5}"
	 * @throws IOException
	 */
	public void addVariables(String jsonString) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
		});
		for (String k : map.keySet()) {
			variables.put(k, map.get(k));
		}
	}

	/**
	 * Adds all members name/value of an object to java contents.
	 * 
	 * @param o
	 * @throws IOException
	 */
	public void addVariables(Object o) throws IOException {
		addVariables(new ObjectMapper().writeValueAsString(o));
	}
	
	/**
	 * Returns the javascript content of the file parsed with the java contents.
	 * 
	 * @return The javascript parsed with the java contents
	 */
	public String parseContent() {
		String res = jsContent;
		for (String k : variables.keySet()) {
			Object v = variables.get(k);
			res = res.replaceAll("\\$\\{" + k + "\\}", v + "");
		}
		return res;
	}

	public void setJsContent(String jsContent) {
		this.jsContent = jsContent;
	}
}

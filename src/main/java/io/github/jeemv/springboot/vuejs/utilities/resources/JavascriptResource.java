package io.github.jeemv.springboot.vuejs.utilities.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/**
 * JavascriptResource This class is part of springBoot-VueJS Allows to
 * Externalize a js script (from resources/static/js) and to use java variables
 * in it.
 * 
 * @author jcheron myaddressmail@gmail.com
 * @version 1.1.0
 *
 */
public class JavascriptResource {
	private JsResourceElement jsResourceElement;
	public JavascriptResource() {
		this(new HashMap<>());
	}

	public JavascriptResource(Map<String, Object> variables) {
		this.jsResourceElement=new JsResourceElement(variables);
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
		return jsResourceElement.put(name, value);
	}

	/**
	 * Adds an array of names,values to the resource.
	 * 
	 * @param keyValues
	 *            Sample new Object[][] { {"id",5},{"name","doe"} }
	 */
	public void addVariables(Object[][] keyValues) {
		jsResourceElement.addVariables(keyValues);
	}

	/**
	 * Adds names,values of contents to the resource from a json array.
	 * 
	 * @param jsonString
	 *            sample "{'id':5}"
	 * @throws IOException
	 */
	public void addVariables(String jsonString) throws IOException {
		jsResourceElement.addVariables(jsonString);
	}

	/**
	 * Adds all members name/value of an object to java contents.
	 * 
	 * @param o
	 * @throws IOException
	 */
	public void addVariables(Object o) throws IOException {
		jsResourceElement.addVariables(o);
	}

	/**
	 * Loads a javascript file.
	 * 
	 * @param filename
	 *            The name of the js file
	 * @throws IOException
	 */
	public void loadFile(String filename) throws IOException {
		JsResourceLoader loader=new JsResourceLoader();
		jsResourceElement.setJsContent(loader.loadFile(filename));
	}

	/**
	 * Returns the javascript content of the file parsed with the java contents.
	 * 
	 * @return The javascript parsed with the java contents
	 */
	public String parseContent() {
		return jsResourceElement.parseContent();
	}

	/**
	 * Returns the javascript content of a javascript file parsed with java
	 * contents.
	 * 
	 * @param filename
	 *            The javascript file to load
	 * @param variables
	 *            The java contents to be parsed.
	 * @return The javascript content of the file parsed with the java contents
	 * @throws IOException
	 */
	public static String load(String filename, Map<String, Object> variables) throws IOException {
		JavascriptResource jsFile = new JavascriptResource(variables);
		jsFile.loadFile(filename);
		return jsFile.parseContent();
	}

	/**
	 * Creates and returns a javascript resource.
	 * 
	 * @param filename
	 *            The javascript file to load
	 * @param variables
	 *            The javascript content of the file parsed with the java
	 *            contents
	 * @return A Javascript resource
	 * @throws IOException
	 */
	public static JavascriptResource create(String filename, Map<String, Object> variables) throws IOException {
		JavascriptResource jsFile = new JavascriptResource(variables);
		jsFile.loadFile(filename);
		return jsFile;
	}

	/**
	 * Creates and returns a javascript resource.
	 * 
	 * @param filename
	 * @return The javascript file to load
	 * @throws IOException
	 */
	public static JavascriptResource create(String filename) throws IOException {
		JavascriptResource jsFile = new JavascriptResource();
		jsFile.loadFile(filename);
		return jsFile;
	}

}

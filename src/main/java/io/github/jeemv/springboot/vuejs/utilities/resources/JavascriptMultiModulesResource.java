package io.github.jeemv.springboot.vuejs.utilities.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A single Javascript file (from resources/static/js) with many modules and java variables.
 * JavascriptMultiModulesResource
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.0
 *
 */
public class JavascriptMultiModulesResource {
	public static String moduleSequenceStart="//";
	public static String moduleSequenceEnd="//";
	private Map<String, JsResourceElement> jsResourceElements;
	private String jsContent;
	
	public JavascriptMultiModulesResource() {
		jsResourceElements=new HashMap<>();
	}
	
	private String getModuleCode(String moduleName) throws Exception {
		Pattern pattern = Pattern.compile(Pattern.quote(moduleSequenceStart)+"(?:.*?)"+moduleName+"(?:.*?)"+Pattern.quote("\r\n")+"(.*?)"+Pattern.quote("\r\n"+moduleSequenceEnd)+"(?:.*?)"+moduleName,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(jsContent);
		if (matcher.find()) {
			String s=matcher.group(1);
			Pattern p = Pattern.compile("(?i)^("+Pattern.quote("(function() {")+")+");
			Matcher m = p.matcher(s);
			if (m.find()) {
				s = m.replaceAll("");
				Pattern pl = Pattern.compile("(?i)("+Pattern.quote("})();")+")$");
				Matcher ml = pl.matcher(s);
				if(ml.find()) {
					s = ml.replaceAll("");
				}else {
					throw new Exception("The module "+moduleName+" contains malformed scripts!");
				}
			}
		    return s;
		}
		throw new Exception("module "+moduleName+"not found!");
		
	}
	
	/**
	 * Gets a module by its name
	 * @param moduleName The module name, defined by // moduleName
	 * @return
	 */
	public JsResourceElement getModule(String moduleName) {
		if(!jsResourceElements.containsKey(moduleName)) {
			jsResourceElements.put(moduleName, new JsResourceElement());
		}
		return jsResourceElements.get(moduleName);
	}
	
	public String parseContent(String moduleName) throws Exception {
		JsResourceElement element=jsResourceElements.get(moduleName);
		element.setJsContent(getModuleCode(moduleName));
		return element.parseContent();
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
		jsContent=loader.loadFile(filename);
	}
	
	/**
	 * Creates and returns a javascript multi modules resource.
	 * 
	 * @param filename
	 * @return The javascript file to load
	 * @throws IOException
	 */
	public static JavascriptMultiModulesResource create(String filename) throws IOException {
		JavascriptMultiModulesResource jsFile = new JavascriptMultiModulesResource();
		jsFile.loadFile(filename);
		return jsFile;
	}
}

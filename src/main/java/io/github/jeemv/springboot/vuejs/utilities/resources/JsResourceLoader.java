package io.github.jeemv.springboot.vuejs.utilities.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * JsResourceLoader
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.0
 *
 */
public class JsResourceLoader {
	private static final String ROOT_FOLDER = "static/js/";

	/**
	 * Loads a javascript file.
	 * 
	 * @param filename
	 *            The name of the js file
	 * @return String
	 * @throws IOException
	 */
	public String loadFile(String filename) throws IOException {
		filename = ROOT_FOLDER + filename + ".js";
		Resource resource = new ClassPathResource(filename);
		InputStream resourceInputStream = resource.getInputStream();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = resourceInputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}
}

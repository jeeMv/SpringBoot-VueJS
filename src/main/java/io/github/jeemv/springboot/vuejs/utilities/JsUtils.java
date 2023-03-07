package io.github.jeemv.springboot.vuejs.utilities;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.beans.RawObject;
import io.github.jeemv.springboot.vuejs.components.VueComponent;
import io.github.jeemv.springboot.vuejs.components.VueProp;
import io.github.jeemv.springboot.vuejs.configuration.VueConfig;
import io.github.jeemv.springboot.vuejs.parts.AbstractVueComposition;
import io.github.jeemv.springboot.vuejs.parts.VueComputed;
import io.github.jeemv.springboot.vuejs.parts.VueComputeds;
import io.github.jeemv.springboot.vuejs.parts.VueDirectives;
import io.github.jeemv.springboot.vuejs.parts.VueFilters;
import io.github.jeemv.springboot.vuejs.parts.VueMethod;
import io.github.jeemv.springboot.vuejs.parts.VueMethods;
import io.github.jeemv.springboot.vuejs.parts.VueProps;
import io.github.jeemv.springboot.vuejs.parts.VueWatcher;
import io.github.jeemv.springboot.vuejs.parts.VueWatchers;
import io.github.jeemv.springboot.vuejs.serializers.AbstractCompositionSerializer;
import io.github.jeemv.springboot.vuejs.serializers.ComputedSerializer;
import io.github.jeemv.springboot.vuejs.serializers.ComputedsSerializer;
import io.github.jeemv.springboot.vuejs.serializers.DirectivesSerializer;
import io.github.jeemv.springboot.vuejs.serializers.FiltersSerializer;
import io.github.jeemv.springboot.vuejs.serializers.MethodSerializer;
import io.github.jeemv.springboot.vuejs.serializers.MethodsSerializer;
import io.github.jeemv.springboot.vuejs.serializers.PropSerializer;
import io.github.jeemv.springboot.vuejs.serializers.PropsSerializer;
import io.github.jeemv.springboot.vuejs.serializers.RawObjectSerializer;
import io.github.jeemv.springboot.vuejs.serializers.VueComponentSerializer;
import io.github.jeemv.springboot.vuejs.serializers.VueJSSerializer;
import io.github.jeemv.springboot.vuejs.serializers.WatcherSerializer;
import io.github.jeemv.springboot.vuejs.serializers.WatchersSerializer;
import io.github.jeemv.springboot.vuejs.utilities.resources.JsResourceLoader;

/**
 * JsUtils This class is part of springBoot-VueJS
 * 
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.1
 *
 */
public class JsUtils {

	/**
	 * Returns a JSON string from an object, using defined serializers
	 * 
	 * @param o the object to parse
	 * @return the JSON string
	 * @throws JsonProcessingException
	 */
	public static String objectToJSON(Object o) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		SimpleModule module = new SimpleModule();
		module.addSerializer(VueJS.class, new VueJSSerializer());
		module.addSerializer(VueComponent.class, new VueComponentSerializer());
		module.addSerializer(VueMethods.class, new MethodsSerializer());
		module.addSerializer(VueMethod.class, new MethodSerializer());
		module.addSerializer(VueComputeds.class, new ComputedsSerializer());
		module.addSerializer(VueComputed.class, new ComputedSerializer());
		module.addSerializer(VueWatchers.class, new WatchersSerializer());
		module.addSerializer(VueWatcher.class, new WatcherSerializer());
		module.addSerializer(VueProp.class, new PropSerializer());
		module.addSerializer(VueProps.class, new PropsSerializer());
		module.addSerializer(AbstractVueComposition.class, new AbstractCompositionSerializer());
		module.addSerializer(VueDirectives.class, new DirectivesSerializer());
		module.addSerializer(VueFilters.class, new FiltersSerializer());
		module.addSerializer(RawObject.class, new RawObjectSerializer());
		objectMapper.registerModule(module);
		if (VueConfig.debug) {
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
		return objectMapper.writeValueAsString(o);
	}

	public static <T> T jsonStringToObject(String jsonString, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T o = mapper.readValue(jsonString, clazz);
		return o;
	}

	public static String cleanJS(String javascriptCode) {
		String result = javascriptCode;
		result = indent(splitLines(result), "\t");
		return result;
	}

	private static String[] splitLines(String javascriptCode) {
		javascriptCode = Matcher.quoteReplacement(javascriptCode);
		Pattern regex = Pattern.compile("\\'[^']*'|(?:\\\"(?:\\\\\\\"|[^\\\"])*\\\")|([;\\\\{\\\\}])");
		Matcher m = regex.matcher(javascriptCode);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			if (m.group(1) != null) {
				if ("}".equals(m.group(1))) {
					m.appendReplacement(b, "SplitHere" + m.group(0) + "SplitHere");
				} else {
					m.appendReplacement(b, m.group(0) + "SplitHere");
				}
			} else
				m.appendReplacement(b, m.group(0).replace("\"", "\\\""));
		}
		m.appendTail(b);
		String replaced = b.toString();
		return replaced.split("SplitHere");
	}

	private static String indent(String[] javascriptLines, String tabulation) {
		StringBuffer sb = new StringBuffer();
		int dec = 0;
		for (String line : javascriptLines) {
			if (!"".equals(line.trim())) {
				if (!line.startsWith("\r\n") && sb.length() > 0) {
					sb.append("\r\n");
				}
				if (dec > 0) {
					sb.append(new String(new char[dec]).replace("\0", tabulation));
				}
				if (line.endsWith("{")) {
					dec++;
				}
				if (line.startsWith("}")) {
					dec--;
				}
				sb.append(line);
			}
		}
		return sb.toString();
	}

	public static String wrapScript(String script) {
		if (script == null || "".equals(script)) {
			return "";
		}
		if (!script.startsWith("<script>")) {
			script = "<script>" + script + "</script>";
		}
		return script;
	}

	public static String loadJsFile(String filename) throws IOException {
		JsResourceLoader loader = new JsResourceLoader("");
		return loader.loadFile(filename);
	}
}

package io.github.jeemv.springboot.vuejs.utilities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.parts.VueComputeds;
import io.github.jeemv.springboot.vuejs.parts.VueMethods;
import io.github.jeemv.springboot.vuejs.serializers.ComputedSerializer;
import io.github.jeemv.springboot.vuejs.serializers.MethodsSerializer;
import io.github.jeemv.springboot.vuejs.serializers.VueJSSerializer;

public class JsUtils {
	public static String objectToJSON(Object o) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    SimpleModule module = new SimpleModule();
	    module.addSerializer(VueJS.class, new VueJSSerializer());
	    module.addSerializer(VueMethods.class, new MethodsSerializer());
	    module.addSerializer(VueComputeds.class, new ComputedSerializer());
	    objectMapper.registerModule(module);
        String v= objectMapper.writeValueAsString(o);
        return v;
	}
	
	public static <T> T jsonStringToObject(String jsonString,Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		T o = mapper.readValue(jsonString,clazz);
		return o;
	}
}

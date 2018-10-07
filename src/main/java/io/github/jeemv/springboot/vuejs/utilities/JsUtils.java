package io.github.jeemv.springboot.vuejs.utilities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.parts.VueComputeds;
import io.github.jeemv.springboot.vuejs.parts.VueMethod;
import io.github.jeemv.springboot.vuejs.parts.VueMethods;
import io.github.jeemv.springboot.vuejs.parts.VueWatchers;
import io.github.jeemv.springboot.vuejs.serializers.ComputedsSerializer;
import io.github.jeemv.springboot.vuejs.serializers.MethodSerializer;
import io.github.jeemv.springboot.vuejs.serializers.MethodsSerializer;
import io.github.jeemv.springboot.vuejs.serializers.VueJSSerializer;
import io.github.jeemv.springboot.vuejs.serializers.WatchersSerializer;

public class JsUtils {
	public static String objectToJSON(Object o) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    SimpleModule module = new SimpleModule();
	    module.addSerializer(VueJS.class, new VueJSSerializer());
	    module.addSerializer(VueMethods.class, new MethodsSerializer());
	    module.addSerializer(VueMethod.class, new MethodSerializer());
	    module.addSerializer(VueComputeds.class, new ComputedsSerializer());
	    module.addSerializer(VueWatchers.class, new WatchersSerializer());
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

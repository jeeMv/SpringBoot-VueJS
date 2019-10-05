package io.github.jeemv.springboot.vuejs.utilities;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.github.jeemv.springboot.vuejs.AbstractVueJS;
import io.github.jeemv.springboot.vuejs.parts.VueHook;
import io.github.jeemv.springboot.vuejs.parts.VuePart;

/**
 * Serialization
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class Serialization {
	
	public static void serializeParts(VuePart value, JsonGenerator gen, SerializerProvider provider,String keyQuote) throws IOException {
		gen.writeStartObject();
		Map<String,Object> parts=value.getElements();
		int max=parts.size();
		int i=0;
		for(Entry<String, Object> entry:parts.entrySet()) {
			String s=keyQuote+entry.getKey()+keyQuote+":"+entry.getValue();
			if(++i<max)
				s+=",";
			gen.writeRaw(s);
		}
        gen.writeEndObject();
	}
	
	public static void serializeParts(VuePart value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		serializeParts(value, gen, provider, "");
	}
	
	public static void serializeVueElements(AbstractVueJS value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if(!value.getData().isEmpty()) {
			gen.writeFieldName("data");
			gen.writeRawValue(value.getData()+"");
		}
		if(!value.getMethods().isEmpty()) {
			gen.writeFieldName("methods");
			gen.writeRawValue(value.getMethods()+"");
		}
		if(!value.getComputed().isEmpty()) {
			gen.writeFieldName("computed");
			gen.writeRawValue(value.getComputed()+"");
		}
		if(!value.getWatchers().isEmpty()) {
			gen.writeFieldName("watch");
			gen.writeRawValue(value.getWatchers()+"");
		}
		if(!value.getDirectives().isEmpty()) {
			gen.writeFieldName("directives");
			gen.writeRawValue(value.getDirectives()+"");
		}
		
		if(!value.getFilters().isEmpty()) {
			gen.writeFieldName("filters");
			gen.writeRawValue(value.getFilters()+"");
		}
		
		for(Map.Entry<String, VueHook> entry:value.getHooks().entrySet()) {
			gen.writeFieldName(entry.getKey());
			gen.writeRawValue(entry.getValue()+"");
		}
	}
}

package io.github.jeemv.springboot.vuejs.utilities;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.github.jeemv.springboot.vuejs.parts.VuePart;

public class Serialization {
	public static void serializeParts(VuePart value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		Map<String,Object> parts=value.getElements();
		int max=parts.size();
		int i=0;
		for(Entry<String, Object> entry:parts.entrySet()) {
			String s=entry.getKey()+":"+entry.getValue();
			if(++i<max)
				s+=",";
			gen.writeRaw(s);
		}
        gen.writeEndObject();
		
	}
}

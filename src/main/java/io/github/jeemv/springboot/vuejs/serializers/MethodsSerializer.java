package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueMethods;

public class MethodsSerializer extends StdSerializer<VueMethods> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MethodsSerializer() {
		this(null);
	}
	
	protected MethodsSerializer(Class<VueMethods> t) {
		super(t);
	}

	@Override
	public void serialize(VueMethods value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		Map<String,Object> methods=value.getElements();
		int max=methods.size();
		int i=0;
		for(Entry<String, Object> entry:methods.entrySet()) {
			String s=entry.getKey()+":"+entry.getValue();
			if(++i<max)
				s+=",";
			gen.writeRaw(s);
		}
        gen.writeEndObject();
		
	}


}

package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueComputeds;

public class ComputedSerializer extends StdSerializer<VueComputeds> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComputedSerializer() {
		this(null);
	}
	
	protected ComputedSerializer(Class<VueComputeds> t) {
		super(t);
	}

	@Override
	public void serialize(VueComputeds value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		Map<String,Object> computeds=value.getElements();
		int max=computeds.size();
		int i=0;
		for(Entry<String, Object> entry:computeds.entrySet()) {
			String s=entry.getKey()+":"+entry.getValue();
			if(++i<max)
				s+=",";
			gen.writeRaw(s);
		}
        gen.writeEndObject();
		
	}


}

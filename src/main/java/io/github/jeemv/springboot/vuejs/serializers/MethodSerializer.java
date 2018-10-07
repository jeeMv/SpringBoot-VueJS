package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueMethod;

public class MethodSerializer extends StdSerializer<VueMethod> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MethodSerializer() {
		this(null);
	}
	
	protected MethodSerializer(Class<VueMethod> t) {
		super(t);
	}

	@Override
	public void serialize(VueMethod value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeRawValue(value+"");
	}


}

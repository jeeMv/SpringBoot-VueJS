package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.beans.RawObject;

public class RawObjectSerializer extends StdSerializer<RawObject> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RawObjectSerializer() {
		this(null);
	}
	
	protected RawObjectSerializer(Class<RawObject> t) {
		super(t);
	}

	@Override
	public void serialize(RawObject value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeRawValue(value+"");
	}


}

package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueDirectives;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;

public class DirectivesSerializer extends StdSerializer<VueDirectives> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectivesSerializer() {
		this(null);
	}
	
	protected DirectivesSerializer(Class<VueDirectives> t) {
		super(t);
	}

	@Override
	public void serialize(VueDirectives value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Serialization.serializeParts(value, gen, provider);
	}


}

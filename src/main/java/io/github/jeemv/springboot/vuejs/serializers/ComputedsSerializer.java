package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueComputeds;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;

public class ComputedsSerializer extends StdSerializer<VueComputeds> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComputedsSerializer() {
		this(null);
	}
	
	protected ComputedsSerializer(Class<VueComputeds> t) {
		super(t);
	}

	@Override
	public void serialize(VueComputeds value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Serialization.serializeParts(value, gen, provider);
	}


}

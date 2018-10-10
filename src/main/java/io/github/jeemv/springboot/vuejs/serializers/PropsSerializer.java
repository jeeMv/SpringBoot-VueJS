package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.jeemv.springboot.vuejs.parts.VueProps;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;

public class PropsSerializer extends StdSerializer<VueProps> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropsSerializer() {
		this(null);
	}
	
	protected PropsSerializer(Class<VueProps> t) {
		super(t);
	}

	@Override
	public void serialize(VueProps value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Serialization.serializeParts(value, gen, provider);
	}


}

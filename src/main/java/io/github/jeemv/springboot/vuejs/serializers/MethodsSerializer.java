package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueMethods;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;

/**
 * MethodsSerializer
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
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
		Serialization.serializeParts(value, gen, provider);
	}


}

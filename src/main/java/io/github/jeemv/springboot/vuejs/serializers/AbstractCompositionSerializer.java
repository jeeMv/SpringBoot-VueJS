package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.AbstractVueComposition;

/**
 * AbstractCompositionSerializer
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class AbstractCompositionSerializer extends StdSerializer<AbstractVueComposition> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractCompositionSerializer() {
		this(null);
	}
	
	protected AbstractCompositionSerializer(Class<AbstractVueComposition> t) {
		super(t);
	}

	@Override
	public void serialize(AbstractVueComposition value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeRawValue(value+"");
	}


}

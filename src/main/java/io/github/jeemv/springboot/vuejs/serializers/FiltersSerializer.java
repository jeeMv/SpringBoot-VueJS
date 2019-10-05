package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueFilters;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;

/**
 * FiltersSerializer
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class FiltersSerializer extends StdSerializer<VueFilters> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FiltersSerializer() {
		this(null);
	}
	
	protected FiltersSerializer(Class<VueFilters> t) {
		super(t);
	}

	@Override
	public void serialize(VueFilters value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Serialization.serializeParts(value, gen, provider);
	}


}

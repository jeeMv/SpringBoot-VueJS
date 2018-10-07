package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueWatchers;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;

public class WatchersSerializer extends StdSerializer<VueWatchers> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WatchersSerializer() {
		this(null);
	}
	
	protected WatchersSerializer(Class<VueWatchers> t) {
		super(t);
	}

	@Override
	public void serialize(VueWatchers value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Serialization.serializeParts(value, gen, provider);
	}


}

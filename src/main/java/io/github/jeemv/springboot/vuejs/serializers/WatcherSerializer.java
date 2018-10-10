package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.parts.VueWatcher;

public class WatcherSerializer extends StdSerializer<VueWatcher> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WatcherSerializer() {
		this(null);
	}
	
	protected WatcherSerializer(Class<VueWatcher> t) {
		super(t);
	}

	@Override
	public void serialize(VueWatcher value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeRawValue(value+"");
	}


}

package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;


public class VueJSSerializer extends StdSerializer<VueJS> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VueJSSerializer() {
		this(null);
	}
	
	protected VueJSSerializer(Class<VueJS> t) {
		super(t);
	}

	@Override
	public void serialize(VueJS value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
        gen.writeStringField("el", value.getEl());
        gen.writeArrayFieldStart("delimiters");
        for (String arg: value.getDelimiters()) {
            gen.writeString(arg);
        }
        gen.writeEndArray();
        Serialization.serializeVueElements(value, gen, provider);
        gen.writeEndObject();
	}


}

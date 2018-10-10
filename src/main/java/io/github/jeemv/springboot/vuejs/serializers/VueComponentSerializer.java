package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.components.VueComponent;
import io.github.jeemv.springboot.vuejs.parts.VueProps;
import io.github.jeemv.springboot.vuejs.utilities.Serialization;


public class VueComponentSerializer extends StdSerializer<VueComponent> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VueComponentSerializer() {
		this(null);
	}
	
	protected VueComponentSerializer(Class<VueComponent> t) {
		super(t);
	}

	@Override
	public void serialize(VueComponent value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		VueProps props=value.getProps();
		gen.writeFieldName("props");
		gen.writeRawValue(props+"");
		Serialization.serializeVueElements(value, gen, provider);
		String template=value.getTemplate();
		if(template!=null && !"".equals(template)) {
			gen.writeStringField("template",template);
		}
        gen.writeEndObject();
	}
}

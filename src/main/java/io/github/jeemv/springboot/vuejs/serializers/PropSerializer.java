package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.components.VueProp;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

public class PropSerializer extends StdSerializer<VueProp> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropSerializer() {
		this(null);
	}
	
	protected PropSerializer(Class<VueProp> t) {
		super(t);
	}

	@Override
	public void serialize(VueProp value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if(value.isSimple()) {
			gen.writeString(value.getName());
		}else {
			gen.writeStartObject();
			if(value.getTypes().size()>0) {
				gen.writeArrayFieldStart("type");
				for(String type:value.getTypes()) {
					gen.writeRawValue(type);
				}
				gen.writeEndArray();
			}
			if(value.isRequired()) {
				gen.writeBooleanField("required", true);
			}
			if(value.getDefaultValue()!=null) {
				gen.writeObjectField("default", value.getDefaultValue());
			}
			if(value.getValidator()!=null) {
				gen.writeFieldName("validator");
				gen.writeRawValue(JsUtils.objectToJSON(value.getValidator()));
			}
			gen.writeEndObject();
		}
	}


}

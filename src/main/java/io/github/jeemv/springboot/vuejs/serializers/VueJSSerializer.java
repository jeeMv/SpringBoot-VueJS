package io.github.jeemv.springboot.vuejs.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;


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
        if(!value.getData().isEmpty()) {
        	gen.writeRaw(",\"data\":"+value.getData()+"");
        }
        if(!value.getMethods().isEmpty()) {
        	gen.writeRaw(",\"methods\":"+JsUtils.objectToJSON(value.getMethods())+"");
        }
        if(!value.getComputed().isEmpty()) {
        	gen.writeRaw(", computed:"+JsUtils.objectToJSON(value.getComputed())+"");
        }
        if(!value.getWatchers().isEmpty()) {
        	gen.writeRaw(", watch:"+JsUtils.objectToJSON(value.getWatchers())+"");
        }
        gen.writeEndObject();
		
	}


}

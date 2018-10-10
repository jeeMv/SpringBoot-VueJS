package io.github.jeemv.springboot.vuejs.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.components.VueProp;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

public class VueProps extends VuePart {
	
	public VueProp add(String name) {
		VueProp prop= new VueProp(name);
		this.elements.put(name,prop);
		return prop;
	}
	
	public boolean isSimple() {
		boolean simple=true;
		List<VueProp> simpleProps=new ArrayList<>();
		Set<Entry<String, Object>> entries = elements.entrySet();
		for(Entry<String, Object> entry:entries) {
			if(!((VueProp)entry.getValue()).isSimple()) {
				simple=false;
			}else {
				simpleProps.add((VueProp) entry.getValue());
			}
		}
		if(!simple) {
			for(VueProp prop:simpleProps) {
				prop.setTypes("String");
			}
		}
		return simple;
	}
	
	/* (non-Javadoc)
	 * @see io.github.jeemv.springboot.vuejs.parts.VuePart#toString()
	 */
	@Override
	public String toString() {
		String props;
		try {
			if(isSimple()) {
				props = JsUtils.objectToJSON(this.elements.values());
			}else {
				props = JsUtils.objectToJSON(this);
			}
			if(props!=null) {
				return props;
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
}

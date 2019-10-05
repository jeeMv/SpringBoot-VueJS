package io.github.jeemv.springboot.vuejs.parts;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueWatcher
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueWatcher {
	private List<VueMethod> handlers;
	private String method=null;
	private boolean deep=false;
	private boolean immediate=false;
	
	public VueWatcher(String methodName) {
		method=methodName;
	}
	
	public VueWatcher(String...methodsBody) {
		handlers=new ArrayList<>();
		for(String body:methodsBody) {
			VueMethod vm=new VueMethod(body, "val","oldVal");
			handlers.add(vm);
			if(methodsBody.length>1) {
				vm.setName("handler"+handlers.size());
			}
		}
	}
	
	@Override
	public String toString() {
		String result="";
		if(method!=null) {
			result="'"+method+"'";
		}else if(handlers!=null){
			if(handlers.size()==1) {
				result= handlers.get(0).toString();
			}else {
				try {
					result= JsUtils.objectToJSON(this.handlers);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return "";
				}
			}
		}
		if(immediate || deep) {
			return "{handler: "+result+",deep: "+deep+",immediate: "+immediate+"}";
		}
		return result;
	}

	public void setDeep(boolean deep) {
		this.deep = deep;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}
}

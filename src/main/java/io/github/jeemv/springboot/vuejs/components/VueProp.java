package io.github.jeemv.springboot.vuejs.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.parts.VueMethod;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * Represents a VueJS property for Components
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueProp {
	private static final String[] jsTypes=new String[] {"String","Number","Boolean","Array","Object","Date","Function","Symbol"};
	private String name;
	private List<String> types;
	private boolean required;
	private Object defaultValue;
	private VueMethod validator;

	
	public VueProp(String name,String type,boolean required,Object defaultValue) {
		this.name=name;
		setTypes(type);
		this.required=required;
		this.defaultValue=defaultValue;
	}
	
	public VueProp(String name,String type,boolean required) {
		this(name,type,required,null);
	}
	
	public VueProp(String name,String type) {
		this(name,type,false,null);
	}
	
	public VueProp(String name) {
		this(name,null,false,null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTypes() {
		return types;
	}

	public void addType(String type) {
		if(type!=null && !"".equals(type)) {
			if(Arrays.asList(jsTypes).contains(type)) {
				if(!types.contains(type))
					this.types.add(type);
			}
		}
	}
	
	public void setTypes(String...types) {
		this.types=new ArrayList<>();
		for(String type:types) {
			addType(type);
		}
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public VueMethod getValidator() {
		return validator;
	}

	public void setValidator(String validatorBody) {
		this.validator = new VueMethod(validatorBody, "value");
	}

	@Override
	public String toString() {
		try {
			return JsUtils.objectToJSON(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
	
	public boolean isSimple() {
		return (!required && (types==null || types.size()==0) && defaultValue==null && validator==null);
	}
	
	
}

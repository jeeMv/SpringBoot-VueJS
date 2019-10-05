package io.github.jeemv.springboot.vuejs.parts;

import java.util.Arrays;
import java.util.List;


/**
 * VueMethod
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueMethod {
	private List<String> params;
	private String body;
	private String name;
	
	public VueMethod(String body,String...params) {
		this.body=body;
		this.params=Arrays.asList(params);
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		String parameters=String.join(",",this.params);
		String thisName="";
		if(name!=null && !"".equals(name)) {
			thisName=" "+name;
		}
		return "function "+thisName+"("+parameters+"){"+body+"}";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

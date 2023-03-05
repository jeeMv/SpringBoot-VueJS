package io.github.jeemv.springboot.vuejs.parts;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * VueMethod
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.1
 *
 */
public class VueMethod {

	private final static Pattern RTRIM = Pattern.compile(";+$");
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

	public VueMethod setParams(List<String> params) {
		this.params = params;
		return this;
	}

	public VueMethod addParams(String...params) {
		this.params.addAll(Arrays.asList(params));
		return this;
	}

	public String getBody() {
		return body;
	}

	public VueMethod setBody(String body) {
		this.body = body;
		return this;
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

	public VueMethod setName(String name) {
		this.name = name;
		return this;
	}

	public VueMethod addBodyParts(String ...body) {
		StringBuilder sb=new StringBuilder();
		for(String line:body) {
			sb.append(RTRIM.matcher(line).replaceFirst(""));
			sb.append(";");
		}
		this.body+=sb.toString();
		return this;
	}

	public VueMethod addBody(String body) {
		this.body+=RTRIM.matcher(body).replaceFirst("")+";";
		return this;
	}

}

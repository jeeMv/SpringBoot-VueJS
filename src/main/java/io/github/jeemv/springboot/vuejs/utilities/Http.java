package io.github.jeemv.springboot.vuejs.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Http {
	
	public static String submitForm(String formRef,String action,String successCallback,String errorCallback) {
		String result=	"let form=this.$refs['"+formRef+"'];"+
						"let formData=form.model;"+request("form.$vnode.data.attrs.method", action, "formData", successCallback,errorCallback);
		return result;
	}
	
	public static String submitForm(String formRef,String action,String successCallback) {
		return submitForm(formRef, action, successCallback, null);
	}
	
	public static String submitForm(String formRef,String successCallback) {
		return Http.submitForm(formRef, "form.$vnode.data.attrs.action", successCallback,null);
	}
	
	public static String request(String method,String url,Object data,String successCallback,String errorCallback){
		if(!url.contains("$")) {
			url="'"+url+"'";
		}
		if(!(data instanceof String)) {
			try {
				data=JsUtils.objectToJSON(data);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		String error="";
		if(!"".equals(errorCallback) && errorCallback!=null) {
			error=",function(response) {"+errorCallback+"}";
		}
		String result=	"this.$http["+method+"]("+url+", "+data+")" + 
						".then(function(response){"+successCallback+"}"+error+");";
		return result;
	}
	
	public static String request(String method,String url,Object data,String successCallback) {
		return request(method, url, data, successCallback, null);
	}
	
	public static String get(String url,String successCallback,String errorCallback) {
		return request("'get'", url, "{}", successCallback, errorCallback);
	}
	
	public static String get(String url,String successCallback) {
		return request("'get'", url, "{}", successCallback, null);
	}
	
	public static String post(String url,Object data,String successCallback,String errorCallback) {
		return request("'post'", url, data, successCallback, errorCallback);
	}
	
	public static String post(String url,Object data,String successCallback) {
		return request("'post'", url, data, successCallback, null);
	}
	
	public static String setRequestHeader(String key, String value) {
		return "this.$http.headers.set('"+key+"', '"+value+"');";
	}

}

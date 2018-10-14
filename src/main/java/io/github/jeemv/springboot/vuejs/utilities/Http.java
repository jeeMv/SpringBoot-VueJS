package io.github.jeemv.springboot.vuejs.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Http class for Http requests
 * @author jcheron
 * @version 1.0.2
 */
public class Http {
	
	private static boolean useAxios=false;
	
	public static void useAxios() {
		useAxios=true;
	}
	
	private static String getJsCode() {
		if(useAxios) {
			useAxios=false; 
			return "Vue.prototype.$http = axios;";
		}
		return "";
	}
	public static String submitForm(String formRef,String action,String successCallback,String errorCallback) {
		String result=	getJsCode()+"let form=this.$refs['"+formRef+"'];"+
						"let formData=form.model;"+request("form.$vnode.data.attrs.method.toLowerCase()", action, "formData", successCallback,errorCallback);
		return result;
	}
	
	public static String submitForm(String formRef,String action,String successCallback) {
		return submitForm(formRef, action, successCallback, null);
	}
	
	/**
	 * Submits a form (form action must be provided on the html form)
	 * @param formRef the ref attribute of the form
	 * @param successCallback the javascript code to execute if success
	 * @return the generated javascript code
	 */
	public static String submitForm(String formRef,String successCallback) {
		return Http.submitForm(formRef, "form.$vnode.data.attrs.action", successCallback,null);
	}
	
	public static String request(String method,String url,Object data,String successCallback,String errorCallback){
		if(!url.contains("$")&& !url.startsWith("this")) {
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
		String result=getJsCode()+"this.$http["+method+"]("+url+", "+data+")" + 
						".then(function(response){"+successCallback+"}"+error+");";
		return result;
	}
	
	public static String request(String method,String url,Object data,String successCallback) {
		return request(method, url, data, successCallback, null);
	}
	
	public static String get(String url,Object data,String successCallback,String errorCallback) {
		return request("'get'", url, data, successCallback, errorCallback);
	}
	
	public static String get(String url,Object data,String successCallback) {
		return request("'get'", url, data, successCallback, null);
	}
	
	public static String get(String url,String successCallback) {
		return request("'get'", url, "{}", successCallback, null);
	}
	
	public static String get(String url,String successCallback,String errorCallback) {
		return request("'get'", url, "{}", successCallback, errorCallback);
	}
	
	public static String delete(String url,Object data,String successCallback,String errorCallback) {
		return request("'delete'", url, data, successCallback, errorCallback);
	}
	
	public static String delete(String url,Object data,String successCallback) {
		return request("'delete'", url, data, successCallback, null);
	}
	
	public static String delete(String url,String successCallback) {
		return request("'delete'", url, "{}", successCallback, null);
	}
	
	public static String delete(String url,String successCallback,String errorCallback) {
		return request("'delete'", url, "{}", successCallback, errorCallback);
	}
	
	public static String put(String url,Object data,String successCallback,String errorCallback) {
		return request("'put'", url, data, successCallback, errorCallback);
	}
	
	public static String put(String url,Object data,String successCallback) {
		return request("'put'", url, data, successCallback, null);
	}
	
	public static String put(String url,String successCallback) {
		return request("'put'", url, "{}", successCallback, null);
	}
	
	public static String put(String url,String successCallback,String errorCallback) {
		return request("'put'", url, "{}", successCallback, errorCallback);
	}
	
	public static String patch(String url,Object data,String successCallback,String errorCallback) {
		return request("'patch'", url, data, successCallback, errorCallback);
	}
	
	public static String patch(String url,Object data,String successCallback) {
		return request("'patch'", url, data, successCallback, null);
	}
	
	public static String patch(String url,String successCallback) {
		return request("'patch'", url, "{}", successCallback, null);
	}
	
	public static String patch(String url,String successCallback,String errorCallback) {
		return request("'patch'", url, "{}", successCallback, errorCallback);
	}
	
	public static String post(String url,Object data,String successCallback,String errorCallback) {
		return request("'post'", url, data, successCallback, errorCallback);
	}
	
	public static String post(String url,Object data,String successCallback) {
		return request("'post'", url, data, successCallback, null);
	}
	
	public static String post(String url,String successCallback) {
		return request("'post'", url, "{}", successCallback, null);
	}
	
	public static String setRequestHeader(String key, String value) {
		return "this.$http.headers.set('"+key+"', '"+value+"');";
	}

}

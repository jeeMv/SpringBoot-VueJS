package io.github.jeemv.springboot.vuejs.utilities;

/**
 * JsArray Javascript array utilities This class is part of springBoot-VueJS
 * 
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.4
 *
 */
public class JsArray {

	/**
	 * Remove an element from array
	 * 
	 * @param array   the array reference
	 * @param element the element to remove
	 * @return
	 */
	public static String remove(String array, String element) {
		return array + ".splice(" + array + ".indexOf(" + element + "), 1);";
	}

	/**
	 * Replace an element at index position in array
	 * 
	 * @param array   the array reference
	 * @param index   The index of the element to replace
	 * @param element The element to replace with
	 * @return
	 */
	public static String replace(String array, String index, String element) {
		return " Object.assign(" + array + "[" + index + "], " + element + ");";
	}

	/**
	 * Adds an element to array
	 * 
	 * @param array   The array reference
	 * @param element The element to add
	 * @return
	 */
	public static String add(String array, String element) {
		return array + ".push(" + element + ");";
	}

	/**
	 * Adds or replace an element in array
	 * 
	 * @param array   The array reference
	 * @param index   The index of the element to replace or -1 for adding
	 * @param element The element to add or replace
	 * @return
	 */
	public static String addOrReplace(String array, String index, String element) {
		return "if(" + index + ">-1){" + replace(array, index, element) + "}else{" + add(array, element) + "}";
	}

	/**
	 * Adds all elements from an array to another
	 * @param array The array reference
	 * @param elements The elements to add
	 * @return
	 */
	public static String addAll(String array,String elements) {
		return array+".push.apply("+array+","+elements+");";
	}

	/**
	 * Remove an element from array by property
	 * @param array The array reference
	 * @param property The property to compare
	 * @param value The value to compare
	 * @return
	 */
	public static String removeByProperty(String array,String property,String value) {
		return array+".splice("+array+".findIndex(e => e."+property+"=="+value+"),1);";
	}

	public static String findByProperty(String array,String property,String value) {
		return array+".find(e => e."+property+"=="+value+");";
	}

	public static String findIndexByProperty(String array,String property,String value) {
		return array+".findIndex(e => e."+property+"=="+value+");";
	}

	public static String containsByProperty(String array,String property,String value) {
		return array+".findIndex(e => e."+property+"=="+value+")>-1;";
	}

	public static String contains(String array,String element) {
		return array+".indexOf("+element+")>-1;";
	}

	public static String letFindByProperty(String variable,String array,String property,String value) {
		return "let "+variable+"="+findByProperty(array,property,value);
	}

	public static String letRemoveByProperty(String variable,String array,String property,String value) {
		return "let "+variable+"="+removeByProperty(array,property,value);
	}
}

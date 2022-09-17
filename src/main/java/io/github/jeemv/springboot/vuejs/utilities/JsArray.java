package io.github.jeemv.springboot.vuejs.utilities;

/**
 * JsArray Javascript array utilities This class is part of springBoot-VueJS
 * 
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.2
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
}

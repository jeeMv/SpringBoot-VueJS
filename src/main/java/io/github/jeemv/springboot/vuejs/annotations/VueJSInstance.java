package io.github.jeemv.springboot.vuejs.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VueJSInstance annotation
 * This class is part of spring-boot-vuejs
 * inject an instance of VueJS initialized from the parameters selector and modelName. 
 * The annotated method must take the ModelMap and VueJS type parameters at the last position.
 * @author jc
 * @since 1.0.2
 *
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VueJSInstance {
	/**
	 * @return the Vue instance element
	 * default: "#app"
	 */
	String selector() default "#app";
	/**
	 * @return the name of the variable added in modelMap
	 * default: "vue"
	 */
	String modelName() default "vue";
}

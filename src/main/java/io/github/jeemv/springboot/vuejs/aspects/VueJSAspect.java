package io.github.jeemv.springboot.vuejs.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.annotations.VueJSInstance;

@Aspect
@Component
public class VueJSAspect {
	@Around("(@annotation(vueJSInstance) && args(..,vue,model)) || "
			+ "(@annotation(vueJSInstance) && args(..,model,vue))")
	public Object injectVueJSOnMethod(ProceedingJoinPoint joinPoint,VueJSInstance vueJSInstance,ModelMap model,VueJS vue) throws Throwable {
	    vue.setEl(vueJSInstance.selector());
		model.addAttribute(vueJSInstance.modelName(), vue);
		System.out.println(joinPoint.getTarget());
	    return joinPoint.proceed();
	}
}

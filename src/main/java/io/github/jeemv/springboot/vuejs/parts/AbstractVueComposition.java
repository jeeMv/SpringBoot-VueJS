package io.github.jeemv.springboot.vuejs.parts;

import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * AbstractVueComposition
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public abstract class AbstractVueComposition {
	protected String name;
	protected boolean internal;
	
	public AbstractVueComposition() {
		this(null,true);
	}
	
	public AbstractVueComposition(String name) {
		this(name,true);
	}
	
	public AbstractVueComposition(String name,boolean internal) {
		this.name=name;
		this.internal=internal;
	}
	
	public String getScript() {
		String script="";
		if(name!=null && !"".equals(name)) {
			script="Vue."+getType()+"('"+name+"',"+this.toString()+");";
			if(!internal)
				script= JsUtils.wrapScript(script);
		}else {
			script=this.toString();
		}
		return script;
	}
	
	public abstract String getType();

}

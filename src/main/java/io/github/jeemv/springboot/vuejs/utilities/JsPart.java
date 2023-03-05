package io.github.jeemv.springboot.vuejs.utilities;

/**
 * JsPart
 *
 * This class is part of springBoot-VueJS
 * @author jcheron
 * @version 1.0.0
 *
 */
public class JsPart {
    private StringBuilder content;

    public JsPart() {
        content = new StringBuilder();
    }

    public JsPart(String jsScript) {
        this.content = new StringBuilder(jsScript);
    }

    /**
     *
     * @param jsScript
     * @return
     */
    public JsPart add(String jsScript) {
        this.content.append(jsScript);
        return this;
    }

    /**
     *
     * @param jsScripts
     * @return
     */
    public JsPart addElements(String... jsScripts) {
        for (String jsScript : jsScripts) {
            this.content.append(jsScript);
        }
        return this;
    }

    public JsPart set(String variable,String value) {
    	this.content.append(variable+"="+value+";");
    	return this;
    }

    public JsPart let(String variable,String value) {
    	this.content.append("let "+variable+"="+value+";");
    	return this;
    }

    public JsPart constante(String variable,String value) {
    	this.content.append("const "+variable+"="+value+";");
    	return this;
    }

    public JsPart var(String variable,String value) {
    	this.content.append("var "+variable+"="+value+";");
    	return this;
    }

    public JsPart ifStatement(String condition,String... jsScripts) {
    	this.content.append("if("+condition+"){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart elseStatement(String... jsScripts) {
    	this.content.append("else{");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart elseIfStatement(String condition,String... jsScripts) {
    	this.content.append("else if("+condition+"){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart forStatement(String variable,String condition,String increment,String... jsScripts) {
    	this.content.append("for("+variable+";"+condition+";"+increment+"){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart whileStatement(String condition,String... jsScripts) {
    	this.content.append("while("+condition+"){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart doWhileStatement(String condition,String... jsScripts) {
    	this.content.append("do{");
    	addElements(jsScripts);
    	this.content.append("}while("+condition+");");
    	return this;
    }

    public JsPart switchStatement(String variable,String... jsScripts) {
    	this.content.append("switch("+variable+"){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart caseStatement(String condition,String... jsScripts) {
    	this.content.append("case "+condition+":");
    	addElements(jsScripts);
    	return this;
    }

    public JsPart defaultStatement(String... jsScripts) {
    	this.content.append("default:");
    	addElements(jsScripts);
    	return this;
    }

    public JsPart forEachStatement(String array,String... jsScripts) {
    	this.content.append(array+".forEach(it=>{");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart forEachStatement(String array,String variable,String... jsScripts) {
    	this.content.append(array+".forEach("+variable+"=>{");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart forEachStatement(String array,String variable,String index,String... jsScripts) {
    	this.content.append(array+".forEach(("+variable+","+index+")=>{");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart tryStatement(String... jsScripts) {
    	this.content.append("try{");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart catchStatement(String variable,String... jsScripts) {
    	this.content.append("catch("+variable+"){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart finallyStatement(String... jsScripts) {
    	this.content.append("finally{");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart function(String name,String... jsScripts) {
    	this.content.append("function "+name+"(){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    public JsPart function(String name,String variables,String... jsScripts) {
    	this.content.append("function "+name+"("+variables+"){");
    	addElements(jsScripts);
    	this.content.append("}");
    	return this;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}

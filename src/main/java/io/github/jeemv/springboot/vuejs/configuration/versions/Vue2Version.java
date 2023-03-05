package io.github.jeemv.springboot.vuejs.configuration.versions;

import io.github.jeemv.springboot.vuejs.configuration.VueVersion;

import java.util.Map;

public class Vue2Version extends VueVersion {

    public Vue2Version(boolean useAxios) {
        super(useAxios);
    }

    @Override
    public String generateVueJSInstance(String internalScript , String el) {
        String script=  "var app = new Vue(\n" +
                internalScript+
                ");\n";
        if (useAxios) {
            script += generateAxios();
        }
        return script;
    }

    protected String generateAxios() {
        return "Vue.prototype.$http=axios;\n";
    }
}

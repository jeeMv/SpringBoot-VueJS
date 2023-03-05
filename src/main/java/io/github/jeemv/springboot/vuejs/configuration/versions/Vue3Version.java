package io.github.jeemv.springboot.vuejs.configuration.versions;

import io.github.jeemv.springboot.vuejs.components.VueComponent;
import io.github.jeemv.springboot.vuejs.configuration.VueVersion;
import io.github.jeemv.springboot.vuejs.parts.AbstractVueComposition;

import java.util.Map;

public class Vue3Version extends VueVersion {

    public Vue3Version(boolean useAxios) {
        super(useAxios);
    }

    @Override
    protected String generateAxios() {
        return "app.config.globalProperties.$http=axios;\n";
    }

    @Override
    public String generateVueJSInstance(String internalScript , String el) {
        String script=  "const app = Vue.createApp(\n" +
                internalScript+
                ");\n";
        if (useAxios) {
            script += generateAxios();
        }
        script+="app.mount('"+el+"');\n";
        return script;
    }
}

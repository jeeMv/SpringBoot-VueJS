package io.github.jeemv.springboot.vuejs.configuration.versions;

import io.github.jeemv.springboot.vuejs.configuration.VueVersion;

import java.util.Map;

public class Vue3Version extends VueVersion {


    public Vue3Version(boolean useAxios, String beforeMountScript) {
        super(useAxios, beforeMountScript);
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
        if (beforeMountScript != null) {
            script += beforeMountScript+"\n";
        }
        script+="app.mount('"+el+"');\n";
        return script;
    }
}

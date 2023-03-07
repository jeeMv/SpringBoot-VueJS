package io.github.jeemv.springboot.vuejs.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jeemv.springboot.vuejs.components.VueComponent;
import io.github.jeemv.springboot.vuejs.parts.AbstractVueComposition;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

import java.util.Map;

public abstract class VueVersion {
    public static final String VUE_2="2.*";
    public static final String VUE_3="3.*";

    protected String beforeMountScript;
    protected final boolean useAxios;

    public VueVersion(boolean useAxios,String beforeMountScript) {
        this.useAxios = useAxios;
        this.beforeMountScript=beforeMountScript;
    }

    public abstract String generateVueJSInstance(String internalScript, String el);

    protected abstract String generateAxios();


}

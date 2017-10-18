package com.bogdan.controllers;

import java.util.HashMap;
import java.util.Map;

public class ApplicationControllerMapper {
    private static Map<String, Class> processors = new HashMap<>();
    static {
        processors.put(null, BasicApplicationController.class);
        processors.put("basic", BasicApplicationController.class);
    }

    public static ApplicationController getRequestProcessor(String application) {
        Class processorClass = processors.get(application);
        if(processorClass != null) {
            try {
                return (ApplicationController) processorClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

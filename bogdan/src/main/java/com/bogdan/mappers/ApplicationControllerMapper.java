package com.bogdan.mappers;

import com.bogdan.controllers.ApplicationController;
import com.bogdan.controllers.BasicApplicationController;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ApplicationControllerMapper {

    private static final Logger LOGGER = Logger.getLogger(ApplicationControllerMapper.class);
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
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.info(e.getMessage());
                for(StackTraceElement el : e.getStackTrace()){
                    LOGGER.info(el);
                }
            }
        }
        return null;
    }
}

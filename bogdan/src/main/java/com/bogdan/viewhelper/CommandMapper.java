package com.bogdan.viewhelper;

import java.util.Map;

public abstract class CommandMapper {

    protected abstract Map<String, Class<? extends Command>> getProcessors();

    public Command getRequestProcessor(String command) {
        Class processorClass = getProcessors().get(command);
        if(processorClass != null) {
            try {
                return (Command)processorClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

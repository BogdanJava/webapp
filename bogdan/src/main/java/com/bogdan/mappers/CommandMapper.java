package com.bogdan.mappers;

import com.bogdan.commands.Command;
import org.apache.log4j.Logger;

import java.util.Map;

public abstract class CommandMapper {

    private final Logger LOGGER = Logger.getLogger(CommandMapper.class);

    protected abstract Map<String, Class<? extends Command>> getProcessors();

    public Command getRequestProcessor(String command) {
        Class processorClass = getProcessors().get(command);
        if(processorClass != null) {
            try {
                return (Command)processorClass.newInstance();
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

package com.bogdan.controllers;

import com.bogdan.mappers.BasicCommandMapper;
import com.bogdan.mappers.CommandMapper;

public class BasicApplicationController extends ApplicationController {
    private final String appName = "basic";

    @Override
    public CommandMapper getCommandMapper() {
        return new BasicCommandMapper();
    }

    @Override
    public String getControllerName() {
        return appName;
    }
}

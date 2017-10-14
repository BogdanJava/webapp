package com.bogdan.controllers;

import com.bogdan.viewhelper.Command;
import com.bogdan.viewhelper.CommandMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ApplicationController {
    public abstract CommandMapper getCommandMapper();

    public abstract String getControllerName();

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");
        if(commandName == null || commandName.equals("")){
            commandName = (String)request.getAttribute("command");
        }
        Command command = getCommandMapper().getRequestProcessor(commandName);
        command.execute(request, response);
    }
}

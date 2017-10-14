package com.bogdan.controllers;

import com.bogdan.viewhelper.UrlMapper;
import com.bogdan.viewhelper.StaticResourceViewHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(isStaticResource(req.getRequestURI())){
            processStaticResource(req, resp);
        }
        else{
            processDynamicResource(req, resp);
        }
    }

    private void processStaticResource(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        new StaticResourceViewHelper().execute(req, resp);
    }

    private boolean isStaticResource(String requestedURI) {
        return requestedURI != null && (requestedURI.endsWith(".css") || requestedURI.endsWith(".js") ||
                requestedURI.endsWith(".html"));
    }

    private void processDynamicResource(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pathInfo = req.getRequestURI().substring(req.getContextPath().length());
        String commandName = req.getParameter("command");

        if (commandName == null || commandName.equals("")) {
            commandName = (String)req.getAttribute("command");
            if(commandName == null || commandName.equals("")){
                commandName = UrlMapper.getCommandName(pathInfo);
            }
            req.setAttribute("command", commandName);
        }
        String app = req.getParameter("application");
        ApplicationController controller = ApplicationControllerMapper.getRequestProcessor(app);
        if(controller != null){
            controller.processRequest(req, resp);
        }
    }
}

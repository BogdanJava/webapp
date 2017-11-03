package com.bogdan.controllers;

import com.bogdan.commands.showpage.GetStaticResourceCommand;
import com.bogdan.commands.showpage.ShowErrorPageCommand;
import com.bogdan.mappers.ApplicationControllerMapper;
import com.bogdan.mappers.UrlMapper;
import com.bogdan.schedule.ScheduleExecutor;
import com.bogdan.templates.AppStringTemplates;
import com.bogdan.utils.LogicUtils;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger("front_logger");

    @Override
    public void init() throws ServletException {
        LogicUtils.initTemplates(this.getServletContext());
        AppStringTemplates.initMapper((ArrayList<StringTemplate>)this.getServletContext().getAttribute("templates"));
        try {
            new ScheduleExecutor().executeSchedule();
        } catch (ParseException | SchedulerException e) {
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (isStaticResource(req.getRequestURI())) {
                processStaticResource(req, resp);
            } else {
                processDynamicResource(req, resp);
            }
        } catch (ServletException | IOException e) {
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
            try {
                req.setAttribute("exception", e);
                new ShowErrorPageCommand().execute(req, resp);
            } catch (ServletException | IOException e1){
                LOGGER.info(e1.getMessage());
                for(StackTraceElement el : e1.getStackTrace()){
                    LOGGER.info(el);
                }
            }
        }
    }

    private void processStaticResource(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        new GetStaticResourceCommand().execute(req, resp);
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

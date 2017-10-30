package com.bogdan.controllers;

import com.bogdan.logic.LogicUtils;
import com.bogdan.logic.ScheduleExecutor;
import com.bogdan.templates.AppStringTemplates;
import com.bogdan.viewhelper.ErrorPageViewHelper;
import com.bogdan.viewhelper.UrlMapper;
import com.bogdan.viewhelper.StaticResourceViewHelper;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
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
            try {
                new ErrorPageViewHelper().execute(req, resp);
            } catch (ServletException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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

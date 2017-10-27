package com.bogdan.logic;

import com.bogdan.viewhelper.Command;
import com.bogdan.viewhelper.ShowContactsViewHelper;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcessSendMail implements Command {

    private final static Logger LOGGER = Logger.getLogger("process_send_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        ArrayList<StringTemplate> templates = (ArrayList<StringTemplate>)req.getServletContext().getAttribute("templates");
        StringTemplate template = null;
        for(StringTemplate t : templates){
            if(t.getName().equals((String)req.getAttribute("template"))){
                template = t;
                break;
            }
        }
        Integer[] ids = LogicUtils.parseStringToInt((String[])req.getAttribute("ids"));
        String topic = (String)req.getAttribute("topic");
        String message = (String)req.getAttribute("text");

        try {
            MailUtils.sendEmail(ids, template, topic, message, req.getServletContext());
        } catch (SQLException | EmailException e) {
            LOGGER.info(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }
        String mes = message != null ? message : template.getTemplate();
        LOGGER.info("Message has been successfully sent: \n" + mes);
        new ShowContactsViewHelper().execute(req, res);
    }
}

package com.bogdan.logic;

import com.bogdan.pojo.Contact;
import com.bogdan.viewhelper.Command;
import com.bogdan.viewhelper.ShowContactsViewHelper;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProcessSendMail implements Command {

    private final static Logger LOGGER = Logger.getLogger("process_send_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        ArrayList<StringTemplate> templates = (ArrayList<StringTemplate>) req.getServletContext().getAttribute("templates");
        StringTemplate template = null;
        for (StringTemplate t : templates) {
            if (t.getName().equals((String) req.getAttribute("template"))) {
                template = t;
                break;
            }
        }
        String topic = (String) req.getAttribute("topic");
        String message = (String) req.getAttribute("text");

        try {
            String[] values;
            Object attribute = req.getAttribute("emailaddresses");
            if(attribute instanceof String[]) values = (String[])attribute;
            else {
                values = new String[1];
                values[0] = (String)attribute;
            }
            ArrayList<Contact> contacts = MailUtils.getContactsByEmails(values);
            Integer[] ids = new Integer[contacts.size()];
            for (int i = 0; i < contacts.size(); i++) {
                ids[i] = contacts.get(i).getId();
            }
            MailUtils.sendEmail(ids, template, topic, message, req.getServletContext());
        } catch (SQLException | EmailException e) {
            LOGGER.info(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                LOGGER.info(el);
            }
        }
        String mes = message != null ? message : template.getTemplate();
        LOGGER.info("Message has been successfully sent: \n" + mes);
        new ShowContactsViewHelper().execute(req, res);
    }
}
package com.bogdan.commands.actions;

import com.bogdan.commands.Command;
import com.bogdan.commands.showpage.ShowContactsCommand;
import com.bogdan.commands.showpage.ShowErrorPageCommand;
import com.bogdan.pojo.EmailData;
import com.bogdan.utils.MailUtils;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SendMailCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger("process_send_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            EmailData emailData = MailUtils.getData(req);
            String sentMessage = MailUtils.sendEmail(emailData);
            LOGGER.info("Message has been successfully sent: \n" + sentMessage);
            new ShowContactsCommand().execute(req, res);
        } catch (SQLException | EmailException e) {
            LOGGER.info(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                LOGGER.info(el);
            }
            new ShowErrorPageCommand().execute(req, res);
        }
    }
}
package com.bogdan.commands.actions;

import com.bogdan.commands.Command;
import com.bogdan.commands.showpage.ShowContactsCommand;
import com.bogdan.commands.showpage.ShowErrorPageCommand;
import com.bogdan.exceptions.DataNotValidException;
import com.bogdan.pojo.Contact;
import com.bogdan.utils.LogicUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class FindCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger("search_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Contact contact = null;
        try {
            contact = LogicUtils.initContact(req);
            LogicUtils.setDateRange(req, contact);
            req.getSession().setAttribute("criteria", contact);
            new ShowContactsCommand().execute(req, res);
        } catch (ParseException | DataNotValidException e) {
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
            req.setAttribute("exception", e);
            new ShowErrorPageCommand().execute(req, res);
        }
    }
}
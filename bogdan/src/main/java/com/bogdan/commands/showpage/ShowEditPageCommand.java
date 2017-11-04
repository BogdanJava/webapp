package com.bogdan.commands.showpage;

import com.bogdan.commands.Command;
import com.bogdan.pojo.PageSpecification;
import com.bogdan.pojo.Row;
import com.bogdan.utils.LogicUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ShowEditPageCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger("modify_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try{
            Integer contactId = Integer.parseInt(req.getParameter("id"));
            Row row = LogicUtils.getRow(contactId);
            if(row.getContact() == null){
                LOGGER.info("Contact with id=" + contactId + " is deleted or not created yet");
                res.sendRedirect("add");
                return;
            }
            req.setAttribute("contact", row.getContact());
            req.setAttribute("phones", row.getPhones());
            req.setAttribute("files", row.getFiles());

            req.setAttribute("title", "Modify contact : " + contactId);
            req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                    "../contents/modifycontact.jsp"));
            req.getRequestDispatcher("common/layout.jsp").forward(req, res);
        } catch (SQLException | NumberFormatException e) {
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
            new ShowAddPageCommand().execute(req, res);
        }
    }
}

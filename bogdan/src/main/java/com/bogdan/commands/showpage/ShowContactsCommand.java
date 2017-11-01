package com.bogdan.commands.showpage;

import com.bogdan.commands.Command;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.PageSpecification;
import com.bogdan.pojo.Row;
import com.bogdan.utils.LogicUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowContactsCommand implements Command {

    private final Logger LOGGER = Logger.getLogger("show_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       try {
            boolean hasCriteria = LogicUtils.hasCriteria(req);
            Contact criteria = LogicUtils.getCriteria(req);
            Integer contactsNumber = LogicUtils.getContactsNumber(criteria);
            Integer pageId = LogicUtils.getPageId(req);
            if(pageId == null){ // if parameter page is incorrect
                res.sendRedirect("contacts?page=1");
                return;
            }
            int from = (pageId - 1) * 10;
            ArrayList<Row> rows = LogicUtils.getLimitedRows(from, criteria);

            req.setAttribute("rows", rows);
            req.setAttribute("hasCriteria", hasCriteria ? "yes" : "no");
            req.setAttribute("maxpage", contactsNumber / 10 + 1);
            req.setAttribute("title", String.format("Contacts %d-%d", from + 1,from + rows.size() ));
            req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                    "../contents/table.jsp"));
            req.getRequestDispatcher("common/layout.jsp").forward(req, res);
        } catch (SQLException | NullPointerException e){
            LOGGER.info(e);
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
            new ShowErrorPageCommand().execute(req, res);
        }
    }
}

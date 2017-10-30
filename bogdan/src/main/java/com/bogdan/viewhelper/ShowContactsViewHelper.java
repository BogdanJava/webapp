package com.bogdan.viewhelper;

import com.bogdan.logic.LogicUtils;
import com.bogdan.pojo.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowContactsViewHelper implements Command{

    private final Logger LOGGER = Logger.getLogger("show_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ArrayList<Row> rows = new ArrayList<>();
        String page = req.getParameter("page");
        Integer pageId = null;
        int from = 0;
        Integer contactsNumber = null;
        if(page == null || page.equals("")) {
            res.sendRedirect("contacts?page=1");
            LOGGER.info("redirected");
            return;
        }
        boolean hasCriteria = LogicUtils.hasCriteria(req);
        LOGGER.info(hasCriteria);
        try {
            Contact criteria = (Contact)req.getSession().getAttribute("criteria");
            LOGGER.info(criteria);
            contactsNumber = LogicUtils.getContactsNumber(criteria);
            if(page.matches("^[0-9]+$")) pageId = Integer.parseInt(page);
            else pageId = 1;

            if(pageId <= 0 || pageId > contactsNumber) {
                pageId = 1;
            }
            from = (pageId - 1) * 10;
            if(criteria != null) {
                rows = LogicUtils.search(criteria, new Limit(from, 10));
                if(rows.size() == 0){
                    rows = LogicUtils.search(criteria, new Limit(0, 10));
                }
            }
            else {
                rows = LogicUtils.getLimitedRows(from, 10);
                if(rows.size() == 0){
                    rows = LogicUtils.getLimitedRows(from, 10);
                }
            }
        } catch (SQLException e){
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }

        req.setAttribute("rows", rows);
        req.setAttribute("hasCriteria", hasCriteria ? "yes" : "no");
        req.setAttribute("maxpage", contactsNumber / 10 + 1);
        req.setAttribute("title", String.format("Contacts %d-%d", from + 1,from + rows.size() ));
        req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                "../contents/table.jsp"));
        req.getRequestDispatcher("common/layout.jsp").forward(req, res);
        return;
    }
}

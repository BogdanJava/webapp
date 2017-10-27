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
        if(page == null) page = "1";
        try {
            pageId = Integer.parseInt(page);
            Integer contactsNumber = LogicUtils.getContactsNumber();
            if(pageId <= 0 || pageId > contactsNumber) {
                pageId = 1;
            }
            from = (pageId - 1) * 10;
            rows = LogicUtils.getLimitedRows(from, 10);
            if(rows.size() == 0){
                rows = LogicUtils.getLimitedRows(0, 10);
            }
        } catch (SQLException e){
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }

        req.getSession().setAttribute("rows", rows);
        req.setAttribute("title", String.format("Contacts %d-%d", from + 1,from + rows.size() ));
        req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                "../contents/table.jsp"));
        req.getRequestDispatcher("common/layout.jsp").forward(req, res);
    }
}

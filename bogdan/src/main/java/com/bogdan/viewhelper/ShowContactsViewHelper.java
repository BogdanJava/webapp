package com.bogdan.viewhelper;

import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
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
        try {
            rows = LogicUtils.getAllRows();
        } catch(SQLException e){
            LOGGER.info("Exception while showing data: " + e);
        }

        req.getSession().setAttribute("rows", rows);
        req.setAttribute("title", "Contacts");
        req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                "../contents/table.jsp"));
        req.getRequestDispatcher("common/layout.jsp").forward(req, res);
    }
}

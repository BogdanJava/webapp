package com.bogdan.viewhelper;

import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
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

    MysqlContactDAO contactDAO = new MysqlContactDAO();
    MysqlPhoneDAO phoneDAO = new MysqlPhoneDAO();
    MysqlFileDAO fileDAO = new MysqlFileDAO();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ArrayList<Row> rows = new ArrayList<>();
        try {
            ArrayList<Contact> contacts = contactDAO.getAll();
            if(contacts != null) {
                for (Contact c : contacts) {
                    ArrayList<Phone> phones = phoneDAO.getAll(c.getId());
                    ArrayList<AttachedFile> files = fileDAO.getAll(c.getId());
                    rows.add(new Row(c, phones, files));
                }
            }
        } catch(SQLException e){
            LOGGER.info("Exception while showing data: " + e);
        }

        req.setAttribute("rows", rows);
        req.setAttribute("title", "Contacts");
        req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                "../contents/table.jsp"));
        req.getRequestDispatcher("common/layout.jsp").forward(req, res);
    }
}

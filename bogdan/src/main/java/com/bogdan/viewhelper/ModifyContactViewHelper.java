package com.bogdan.viewhelper;

import com.bogdan.dao.GenericDAO;
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

public class ModifyContactViewHelper implements Command {

    private static final Logger LOGGER = Logger.getLogger("modify_logger");

    private GenericDAO<Contact> contactDAO = new MysqlContactDAO();
    private GenericDAO<Phone> phoneDAO = new MysqlPhoneDAO();
    private GenericDAO<AttachedFile> fileDAO = new MysqlFileDAO();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer contactId = null;
        Contact contact = null;
        ArrayList<Phone> phones = null;
        ArrayList<AttachedFile> files = null;
        try{
            contactId = Integer.parseInt(req.getParameter("id"));
            contact = contactDAO.find(new Contact(contactId),null).get(0);
            phones = phoneDAO.find(new Phone(contactId),null);
            files = fileDAO.find(new AttachedFile(contactId),null);

        } catch(NumberFormatException e){
            new AddContactPageViewHelper().execute(req, res);
            return;
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }

        req.setAttribute("contact", contact);
        req.setAttribute("phones", phones);
        req.setAttribute("files", files);

        req.setAttribute("title", "Modify contact : " + contactId);
        req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                    "../contents/modifycontact.jsp"));
        req.getRequestDispatcher("common/layout.jsp").forward(req, res);
    }
}

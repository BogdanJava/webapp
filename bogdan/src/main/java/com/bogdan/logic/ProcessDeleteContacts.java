package com.bogdan.logic;

import com.bogdan.dao.GenericDAO;
import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;
import com.bogdan.viewhelper.Command;
import com.bogdan.viewhelper.ShowContactsViewHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProcessDeleteContacts implements Command {

    private final Logger LOGGER = Logger.getLogger("delete_logger");

    private GenericDAO<Contact> contactDAO = new MysqlContactDAO();
    private GenericDAO<Phone> phoneDAO = new MysqlPhoneDAO();
    private GenericDAO<AttachedFile> fileDAO = new MysqlFileDAO();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer[] deletedContactsId = LogicUtils.parseStringToInt((String[])req.getAttribute("toDelete"));
        try {
            for (int contactId : deletedContactsId) {
                contactDAO.delete(contactId);
                phoneDAO.delete(contactId);
                fileDAO.delete(contactId);
                LOGGER.info("Contact has been deleted(id = " + contactId + ")\n");
            }
        } catch (SQLException e){
            LOGGER.info("Exception while deleting contact: " + e);
        }
        new ShowContactsViewHelper().execute(req, res);
    }
}

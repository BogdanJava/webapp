package com.bogdan.logic;

import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;
import com.bogdan.dao.GenericDAO;
import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
import com.bogdan.viewhelper.Command;
import com.bogdan.viewhelper.ShowContactsViewHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ProcessAddContact implements Command{

    private final static Logger LOGGER = Logger.getLogger("adding_logger");
    private GenericDAO<Contact> contactDao = new MysqlContactDAO();
    private GenericDAO<Phone> phoneDao = new MysqlPhoneDAO();
    private GenericDAO<AttachedFile> fileDao = new MysqlFileDAO();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        StringBuilder addProcessLog = new StringBuilder();
        try {
            Contact contact = LogicUtils.initContact(req);
            if(contact.getPhoto() != null)
                LogicUtils.createFile(contact, contact.getPhoto());
            contact.setId(contactDao.insert(contact));
            if(contact.getId() != -1) {
                addProcessLog.append("Contact inserted: " + contact.toString() + "\n");
                ArrayList<Phone> phones = LogicUtils.initPhones(req, contact.getId());
                ArrayList<AttachedFile> files = LogicUtils.initFiles(req, contact.getId());
                for (Phone phone : phones) {
                    phoneDao.insert(phone);
                    addProcessLog.append("Phone inserted: " + phone.toString() + "\n");
                }
                for (AttachedFile file : files) {
                    LogicUtils.createFile(contact, file);
                    fileDao.insert(file);
                }
                addProcessLog.append("Number of attached files: " + files.size() + "\n");
            }
            else {
                addProcessLog.insert(0, "There was some error while adding contact.\n");
            }
        } catch (SQLException | ParseException e) {
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el + "\n");
            }
        }

        LOGGER.info(addProcessLog.toString());
        new ShowContactsViewHelper().execute(req, res);
    }
}
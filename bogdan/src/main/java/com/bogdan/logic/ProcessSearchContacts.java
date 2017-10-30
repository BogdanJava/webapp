package com.bogdan.logic;

import com.bogdan.dao.GenericDAO;
import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;
import com.bogdan.pojo.Row;
import com.bogdan.viewhelper.Command;
import com.bogdan.viewhelper.ShowContactsViewHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ProcessSearchContacts implements Command {

    private static final Logger LOGGER = Logger.getLogger("search_logger");

    private GenericDAO<Contact> contactDao = new MysqlContactDAO();
    private GenericDAO<Phone> phoneDao = new MysqlPhoneDAO();
    private GenericDAO<AttachedFile> fileDao = new MysqlFileDAO();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Contact contact = null;
        ArrayList<Row> rows = null;
        try {
            contact = LogicUtils.initContact(req);
            LogicUtils.setDateRange(req, contact);
            ArrayList<Contact> contacts = contactDao.find(contact,null);
            rows = new ArrayList<>();
            if(contacts != null)
            for(int i=0; i<contacts.size(); i++){
                ArrayList<Phone> phones = phoneDao.find(new Phone(contacts.get(i).getId()),null);
                ArrayList<AttachedFile> files = fileDao.find(new AttachedFile(contacts.get(i).getId()),null);
                rows.add(new Row(contacts.get(i), phones, files));
            }
        } catch (ParseException | SQLException e) {
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }
        req.getSession().setAttribute("criteria", contact);
        req.setAttribute("rows", rows);
        new ShowContactsViewHelper().execute(req, res);
    }
}
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
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ProcessModifyContact implements Command {

    private final static Logger LOGGER = Logger.getLogger("edit_logger");

    private MysqlContactDAO contactDAO = new MysqlContactDAO();
    private MysqlPhoneDAO phoneDAO = new MysqlPhoneDAO();
    private MysqlFileDAO fileDAO = new MysqlFileDAO();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();

        ArrayList<String> deletedPhoneIds = (ArrayList<String>)req.getAttribute("deletedPhones");
        ArrayList<String> deletedFilesIds = (ArrayList<String>)req.getAttribute("deletedFiles");

        LOGGER.info("Files: " + deletedFilesIds.size() + ", phones: " + deletedPhoneIds.size());

        try {
            Integer contactId = Integer.parseInt((String)req.getAttribute("editContactId"));
            Contact contact = LogicUtils.initContact(req);
            contact.setId(contactId);

            ArrayList<Phone> phones = LogicUtils.initPhones(req, contactId);
            ArrayList<AttachedFile> files = LogicUtils.initFiles(req, contactId);

            if(!LogicUtils.createFile(contact, contact.getPhoto())) {
                AttachedFile oldPhoto = contactDAO.find(new Contact(contactId)).get(0).getPhoto();
                if(new File(LogicUtils.getAbsoluteOfRelative(oldPhoto.getRelativePath())).exists())
                    contact.setPhoto(oldPhoto);
                else contact.setPhoto(null);
            }

            if(contactDAO.update(contactId, contact))
            builder.append("Contact #" + contactId + " updated: " + contact);

            for(String id : deletedPhoneIds){
                phoneDAO.delete(Integer.parseInt(id));
                builder.append("Phone #" + id + "of contact #" + contactId + " deleted");
            }
            for(String id : deletedFilesIds){
                fileDAO.delete(Integer.parseInt(id));
                builder.append("File #" + id + "of contact #" + contactId + " deleted");
            }

            for(AttachedFile file : files){
                if(LogicUtils.createFile(contact, file)) {
                    fileDAO.insert(file);
                    builder.append("New file " + file + " inserted");
                }
            }

            for(Phone phone : phones){

                phoneDAO.insert(phone);
                builder.append("New phone " + phone + " inserted");
            }

            LOGGER.info(builder.toString());

            new ShowContactsViewHelper().execute(req, res);
        } catch (ParseException | SQLException e){
            LOGGER.info("Exception while updating file: " + e);
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }
    }
}
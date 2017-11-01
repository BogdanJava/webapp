package com.bogdan.utils;

import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Limit;
import com.bogdan.pojo.Phone;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class CRUDUtils {

    private static final Logger LOGGER = Logger.getLogger("crud_logger");

    private static MysqlContactDAO contactDAO = new MysqlContactDAO();
    private static MysqlPhoneDAO phoneDAO = new MysqlPhoneDAO();
    private static MysqlFileDAO fileDAO = new MysqlFileDAO();

    public static boolean insertContact(Contact contact) throws ParseException, IOException, SQLException {
        if(contact.getPhoto() != null)
            LogicUtils.createFile(contact, contact.getPhoto());

        contact.setId(contactDAO.insert(contact));
        return contact.getId() != -1;
    }

    public static ArrayList<Phone> insertPhones(HttpServletRequest req, Contact contact) throws SQLException {
        ArrayList<Phone> phones = LogicUtils.initPhones(req, contact.getId());
        for (Phone phone : phones) {
            phoneDAO.insert(phone);
        }
        return phones;
    }

    public static ArrayList<AttachedFile> insertFiles(HttpServletRequest req, Contact contact) throws SQLException, IOException {
        ArrayList<AttachedFile> files = LogicUtils.initFiles(req, contact.getId());
        for (AttachedFile file : files) {
            LogicUtils.createFile(contact, file);
            fileDAO.insert(file);
        }
        return files;
    }

    public static void deleteContact(Integer[] deletedContactsId) throws SQLException {
        for (int contactId : deletedContactsId) {
            contactDAO.delete(contactId);
            phoneDAO.delete(contactId);
            fileDAO.delete(contactId);
        }
    }

    public static ArrayList<Phone> deletePhones(Integer[] deletedPhonesIds) throws SQLException {
        ArrayList<Phone> deletedPhones = new ArrayList<>();
        for(int id : deletedPhonesIds){
            Phone p = new Phone();
            p.setId(id);
            deletedPhones.add(phoneDAO.find(p, null).get(0));
            phoneDAO.delete(id);
        }
        return deletedPhones;
    }

    public static ArrayList<AttachedFile> deleteFiles(Integer[] deletedFilesIds) throws SQLException {
        ArrayList<AttachedFile> deletedFiles = new ArrayList<>();
        for(int id : deletedFilesIds){
            LOGGER.info(id);
            AttachedFile f = new AttachedFile();
            f.setId(id);
            deletedFiles.add(fileDAO.find(f, null).get(0));
            fileDAO.delete(id);
        }
        return deletedFiles;
    }

    public static ArrayList<Contact> getContactByCriteria(Contact criteria, Limit limit) throws SQLException {
        return contactDAO.find(criteria, limit);
    }

    public static boolean commitContactChanges(Contact contact) throws IOException, SQLException {
        if(!LogicUtils.createFile(contact, contact.getPhoto())) {
            AttachedFile oldPhoto = contactDAO.find(new Contact(contact.getId()),null).get(0).getPhoto();
            File f = new File(LogicUtils.getAbsoluteOfRelative(oldPhoto.getRelativePath()));
            if(f.exists()){
                if(oldPhoto.equals(contact.getPhoto())) {
                    f.delete();
                }
                contact.setPhoto(oldPhoto);
            }
        }
        contactDAO.update(contact.getId(), contact);
        return true;
    }



}

package com.bogdan.logic;

import com.bogdan.dao.GenericDAO;
import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.pojo.Contact;
import com.bogdan.templates.AbstractTemplate;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.mail.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MailUtils {
    private static final Logger LOGGER = Logger.getLogger("mailutils_logger");

    public static String getProperty(String propname){
        Properties properties = new Properties();
        FileInputStream fis = null;
        String propertyValue = null;
        try {
            fis = new FileInputStream(MailUtils.class.getResource(
                    "/email.properties").getPath());
            properties.load(fis);
            propertyValue = properties.getProperty(propname);
            if(fis != null) fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    public static ArrayList<Contact> getContactsByEmails(String[] emails) throws SQLException {
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        ArrayList<Contact> contacts = new ArrayList<>();
        for(String email : emails){
            LOGGER.info("EMAIL: " + email);
            Contact criteria = new Contact();
            criteria.setEmail(email);
            Contact contact = contactDAO.find(criteria, null).get(0);
            if(contact != null) contacts.add(contact);
        }
        return contacts;
    }

    public static void sendEmail(ArrayList<Contact> contacts, AbstractTemplate template) throws EmailException {
        for(Contact contact : contacts) {
            Email email = MailUtils.getStandardEmail();
            email.addTo(contact.getEmail());
            email.setSubject(template.getSubject());
            Map<String, String> attributes = new HashMap<>();
            attributes.put("username", contact.getFullName());
            attributes.put("respath", MailUtils.getProperty("respath"));
            attributes.put("from", MailUtils.getProperty("username"));
            template.setAttributes(attributes);
            email.setMsg(template.getHtml());
            email.send();
        }
    }

    public static void sendEmail(Integer[] ids, StringTemplate template, String topic, String message,
                                 ServletContext context) throws SQLException, EmailException {
            if (template != null) {
                sendEmail(ids, template, topic, context);
            } else {
                sendEmail(ids, message, topic);
            }
    }

    private static void sendEmail(Integer[] ids, StringTemplate t, String topic, ServletContext context)
            throws SQLException, EmailException {
        GenericDAO<Contact> contactDAO = new MysqlContactDAO();
        ArrayList<Contact> contacts = new ArrayList<>();
        for(Integer id : ids){
            contacts.add(contactDAO.find(new Contact(id), null).get(0));
        }

        for(Contact contact : contacts){
            AbstractTemplate template = AbstractTemplate.getInstance(t.getName(), context);
            Map<String, String> attributes = new HashMap<>();
            Email email = MailUtils.getStandardEmail();
            email.addTo(contact.getEmail());
            email.setSubject(topic);
            switch (t.getName()){
                case "adv": {
                    attributes.put("username", contact.getFullName());
                    attributes.put("respath", MailUtils.getProperty("respath"));
                    break;
                }
                case "birthday": {
                    attributes.put("username", contact.getFullName());
                    attributes.put("from", MailUtils.getProperty("username"));
                    break;
                }
            }
            template.setAttributes(attributes);
            email.setMsg(template.getHtml());
            LOGGER.info(template.getHtml());
            email.send();
        }
    }

    private static void sendEmail(Integer[] ids, String message, String topic) throws SQLException, EmailException {
        GenericDAO<Contact> contactDAO = new MysqlContactDAO();
        Set<String> emails = new HashSet<>();
        for(Integer id : ids){
            emails.add(contactDAO.find(new Contact(id), null).get(0).getEmail());
        }
        Email email = MailUtils.getStandardEmail();
        email.setCharset("utf-8");
        email.setMsg(message);
        email.setSubject(topic);
        for(String str : emails){
            email.addTo(str);
        }
        email.send();
    }

    private static Email getStandardEmail() throws EmailException {
        Email email = new HtmlEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(MailUtils.getProperty("username"),
                MailUtils.getProperty("password")));
        email.setSSL(true);
        email.setFrom(MailUtils.getProperty("username"));
        return email;
    }

}
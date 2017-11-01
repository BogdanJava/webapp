package com.bogdan.utils;

import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.EmailData;
import com.bogdan.templates.AbstractTemplate;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MailUtils {
    private static final Logger LOGGER = Logger.getLogger("mailutils_logger");

    private static String getProperty(String propname) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(MailUtils.class.getResource(
                    "/email.properties").getPath());
            properties.load(fis);
            return properties.getProperty(propname);
        } finally {
            if(fis != null) fis.close();
        }
    }

    public static ArrayList<String> getEmailsByContacts(ArrayList<Contact> contacts){
        ArrayList<String> list = new ArrayList<>();
        for(Contact c : contacts){
            list.add(c.getEmail());
        }
        return list;
    }

    public static ArrayList<Contact> getContactsByEmails(String[] emails) throws SQLException {
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        ArrayList<Contact> contacts = new ArrayList<>();
        for(String email : emails){
            Contact criteria = new Contact();
            criteria.setEmail(email);
            Contact contact = contactDAO.find(criteria, null).get(0);
            if(contact != null) contacts.add(contact);
        }
        return contacts;
    }

    public static String sendEmail(EmailData emailData) throws SQLException, EmailException, IOException {
            if (emailData.getTemplate() != null) {
                return sendTemplateEmail(emailData);
            } else {
                return sendCommonEmail(emailData);
            }
    }

    private static String sendTemplateEmail(EmailData emailData)
            throws SQLException, EmailException, IOException {
        AbstractTemplate template = AbstractTemplate.getInstance(emailData.getTemplate().getName());

        for(String contact : emailData.getSubscribers()){
            Map<String, String> attributes = new HashMap<>();
            Email email = MailUtils.getStandardEmail();
            email.addTo(contact);
            if(emailData.getTopic() == null || emailData.getMessage().equals("")) emailData.setTopic(template.getStandardTopic());
            email.setSubject(emailData.getTopic());
            switch (emailData.getTemplate().getName()){
                case "adv": {
                    attributes.put("username", contact);
                    attributes.put("respath", MailUtils.getProperty("respath"));
                    break;
                }
                case "birthday": {
                    attributes.put("username", contact);
                    attributes.put("from", MailUtils.getProperty("username"));
                    break;
                }
            }
            template.setAttributes(attributes);
            email.setMsg(template.getHtml());
            email.send();
        }
        return template.getSample();
    }

    private static String sendCommonEmail(EmailData emailData) throws SQLException, EmailException, IOException {
        Email email = MailUtils.getStandardEmail();
        for(String emailAddress : emailData.getSubscribers()){
            email.addTo(emailAddress);
        }
        email.setCharset("utf-8");
        if(emailData.getMessage() == null || emailData.getMessage().equals("")) emailData.setMessage("Empty message");
        email.setMsg(emailData.getMessage());
        if(emailData.getTopic() == null || emailData.getMessage().equals("")) emailData.setTopic("No subject");
        email.setSubject(emailData.getTopic());
        email.send();
        return emailData.getMessage();
    }

    private static Email getStandardEmail() throws EmailException, IOException {
        Email email = new HtmlEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(MailUtils.getProperty("username"),
                MailUtils.getProperty("password")));
        email.setSSL(true);
        email.setFrom(MailUtils.getProperty("username"));
        return email;
    }

    public static Integer[] getIdsFromPage(HttpServletRequest req){
        String[] parameters = req.getParameterValues("emails");
        if(parameters != null) {
            Integer[] ids = new Integer[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                ids[i] = Integer.parseInt(parameters[i]);
            }
            return ids;
        } else return new Integer[0];
    }

    public static EmailData getData(HttpServletRequest req) throws SQLException {
        StringTemplate selectedTemplate = LogicUtils.getSelectedTemplate(req);
        String topic = (String) req.getAttribute("topic");
        String message = (String) req.getAttribute("text");
        String[] emailAddresses = LogicUtils.getEmails(req);

        EmailData data = new EmailData();
        data.setSubscribers(new ArrayList<>(Arrays.asList(emailAddresses)));
        data.setMessage(message);
        data.setTemplate(selectedTemplate);
        data.setTopic(topic);

        return data;
    }

}
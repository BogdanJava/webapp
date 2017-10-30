package com.bogdan.logic.tasks;

import com.bogdan.dao.GenericDAO;
import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.logic.MailUtils;
import com.bogdan.pojo.Contact;
import com.bogdan.templates.AppStringTemplates;
import com.bogdan.templates.BirthdayTemplate;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AutoBirthdayMail implements Job {

    private final Logger LOGGER = Logger.getLogger("autobday_logger");

    public static String getName(){
        return "SendBirthdayMail";
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Contact contact = new Contact();
        contact.setBirthDate(new Date());

        MysqlContactDAO contactDAO = new MysqlContactDAO();
        try {
            ArrayList<Contact> contacts = contactDAO.getBirthdays(contact);

            LOGGER.info(contacts.size());

            MailUtils.sendEmail(contacts, new BirthdayTemplate("birthday"));
            LOGGER.info("Mail has been sent automatically.");

        } catch (SQLException | EmailException e) {
            LOGGER.info(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                LOGGER.info(el);
            }
        }
    }
}

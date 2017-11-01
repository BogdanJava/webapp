package com.bogdan.schedule.tasks;

import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.EmailData;
import com.bogdan.templates.AppStringTemplates;
import com.bogdan.utils.MailUtils;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
            EmailData emailData = new EmailData();
            emailData.setSubscribers(MailUtils.getEmailsByContacts(contacts));
            emailData.setTopic("Happy b-day to You!");
            emailData.setTemplate(AppStringTemplates.getTemplateByName("birthday"));

            String sentMessage = MailUtils.sendEmail(emailData);
            LOGGER.info("Mail has been sent automatically.\r\n" + sentMessage);

        } catch (SQLException | EmailException | IOException e) {
            LOGGER.info(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                LOGGER.info(el);
            }
        }
    }
}

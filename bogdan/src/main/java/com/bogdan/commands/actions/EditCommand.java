package com.bogdan.commands.actions;

import com.bogdan.commands.Command;
import com.bogdan.commands.showpage.ShowContactsCommand;
import com.bogdan.commands.showpage.ShowErrorPageCommand;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Row;
import com.bogdan.utils.CRUDUtils;
import com.bogdan.utils.LoggerUtils;
import com.bogdan.utils.LogicUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class EditCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger("edit_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String log;
        ArrayList<String> deletedPhoneIds = (ArrayList<String>)req.getAttribute("deletedPhones");
        ArrayList<String> deletedFilesIds = (ArrayList<String>)req.getAttribute("deletedFiles");
        try {
            Contact contact = LogicUtils.initContact(req);
            Row delRow = new Row();
            delRow.setContact(contact);
            Row insRow = new Row();

            if(CRUDUtils.commitContactChanges(contact)) {
                delRow.setPhones(CRUDUtils.deletePhones(LogicUtils.parseIdsFromList(deletedPhoneIds)));
                delRow.setFiles(CRUDUtils.deleteFiles(LogicUtils.parseIdsFromList(deletedFilesIds)));
                insRow.setFiles(CRUDUtils.insertFiles(req, contact)); // insert new files and phones
                insRow.setPhones(CRUDUtils.insertPhones(req, contact));
            }
            LOGGER.info(LoggerUtils.getEditLog(delRow, insRow));
        } catch (ParseException | SQLException e){
            LOGGER.info("Exception while updating file: " + e);
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
            new ShowErrorPageCommand().execute(req, res);
            return;
        }
        new ShowContactsCommand().execute(req, res);
    }
}
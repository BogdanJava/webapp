package com.bogdan.commands.actions;

import com.bogdan.commands.Command;
import com.bogdan.commands.showpage.ShowContactsCommand;
import com.bogdan.exceptions.DataNotValidException;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;
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

public class AddCommand implements Command{

    private final static Logger LOGGER = Logger.getLogger("adding_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String insertLog = null;
        try {
            Contact contact = LogicUtils.initContact(req);
            if(CRUDUtils.insertContact(contact)) { // if contact has been added to date base;
                ArrayList<Phone> phones = CRUDUtils.insertPhones(req, contact);
                ArrayList<AttachedFile> files = CRUDUtils.insertFiles(req, contact);
                insertLog = LoggerUtils.getInsertLog(new Row(contact, phones, files));
            }
            else {
                insertLog =  "There was some error while adding contact.\n";
            }
        } catch (SQLException | ParseException | DataNotValidException e) {
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el + "\n");
            }
        }
        LOGGER.info(insertLog);
        new ShowContactsCommand().execute(req, res);
    }
}
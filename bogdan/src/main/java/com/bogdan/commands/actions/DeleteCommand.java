package com.bogdan.commands.actions;

import com.bogdan.commands.Command;
import com.bogdan.commands.showpage.ShowContactsCommand;
import com.bogdan.commands.showpage.ShowErrorPageCommand;
import com.bogdan.utils.CRUDUtils;
import com.bogdan.utils.LogicUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteCommand implements Command {

    private final Logger LOGGER = Logger.getLogger("delete_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            CRUDUtils.deleteContact(LogicUtils.getIdsForDelete(req));
            new ShowContactsCommand().execute(req, res);
        } catch (SQLException e){
            LOGGER.info("Exception while deleting contact: " + e);
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
            new ShowErrorPageCommand().execute(req, res);
        }
    }
}

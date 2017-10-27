package com.bogdan.viewhelper;

import com.bogdan.logic.LogicUtils;
import com.bogdan.pojo.*;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class SendMailViewHelper implements Command {

    private final Logger LOGGER = Logger.getLogger("send_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ArrayList<Row> rows = new ArrayList<>();
        try {
            ArrayList<Contact> contacts = LogicUtils.getContactsById(req);
            rows = new ArrayList<>();
            for(Contact c : contacts){
                ArrayList<Phone> phones = LogicUtils.getPhonesByContact(c);
                ArrayList<AttachedFile> files = LogicUtils.getFilesByContact(c);
                rows.add(new Row(c, phones, files));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }
        req.setAttribute("rows", rows);
        req.setAttribute("title", "Send mail");
        req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                "../contents/send.jsp"));
        req.getRequestDispatcher("common/layout.jsp").forward(req, res);
    }
}

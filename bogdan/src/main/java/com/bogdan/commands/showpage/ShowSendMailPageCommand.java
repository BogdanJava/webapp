package com.bogdan.commands.showpage;

import com.bogdan.commands.Command;
import com.bogdan.pojo.PageSpecification;
import com.bogdan.pojo.Row;
import com.bogdan.utils.LogicUtils;
import com.bogdan.utils.MailUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowSendMailPageCommand implements Command {

    private final Logger LOGGER = Logger.getLogger("send_logger");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Integer[] ids = MailUtils.getIdsFromPage(req);
            ArrayList<Row> rows = new ArrayList<>();
            for(Integer id : ids){
                rows.add(LogicUtils.getRow(id));
            }

            req.setAttribute("rows", rows);
            req.setAttribute("title", "Send mail");
            req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                    "../contents/send.jsp"));
            req.getRequestDispatcher("common/layout.jsp").forward(req, res);
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                LOGGER.info(el);
            }
            new ShowErrorPageCommand().execute(req, res);
        }
    }
}

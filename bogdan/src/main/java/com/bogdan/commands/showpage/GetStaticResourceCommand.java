package com.bogdan.commands.showpage;

import com.bogdan.commands.Command;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GetStaticResourceCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getResourceAsStream(req.getRequestURI());
            os = res.getOutputStream();
            IOUtils.copy(is, os);
        }
        finally {
            if(is != null) {
                is.close();
            }
            if(os != null) {
                os.close();
            }
        }
    }
}


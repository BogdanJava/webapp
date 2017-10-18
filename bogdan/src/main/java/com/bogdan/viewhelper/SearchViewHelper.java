package com.bogdan.viewhelper;

import com.bogdan.pojo.PageSpecification;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchViewHelper implements Command{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("title", "Поиск");
        req.setAttribute("page", new PageSpecification("footer.jsp", "header.jsp",
                "../contents/search.jsp"));
        req.getRequestDispatcher("common/layout.jsp").forward(req, res);
    }
}

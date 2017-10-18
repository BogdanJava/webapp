package com.bogdan.filter;

import com.bogdan.logic.LogicUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(filterName = "standardEncodeFilter")
public class StandardEncodeFilter extends BaseEncodeFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String contentType = servletRequest.getContentType();

        if(contentType == null ||
                contentType.equalsIgnoreCase("application/x-www-form-urlencoded")) {
            LogicUtils.parseParameters((HttpServletRequest) servletRequest);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

package com.bogdan.filter;

import com.bogdan.utils.LogicUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

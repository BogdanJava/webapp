package com.bogdan.filter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "loggingFilter")
public class LoggingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger("server_logger");

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private void logRequest(ServletRequest request){
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        Map<String, String[]> params = request.getParameterMap();
        StringBuilder builder = new StringBuilder();
        Enumeration<String> headers = httpServletRequest.getHeaderNames();
        while(headers.hasMoreElements()){
            String header = headers.nextElement();
            String headerValue = httpServletRequest.getHeader(header);
            builder.append("\nHeader " + header + " has value " + headerValue);
        }
        builder.append("\nRequest headers are :" + params.size());
        builder.append("\nTotal amount of request parameters is:" + params.size());
        for(String param : params.keySet()) {
            builder.append("\nParameter " + param + " has value " + params.get(param)[0]);
        }
        LOGGER.info(builder.toString());
    }

    private void logResponse(ServletResponse response){
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        StringBuilder builder = new StringBuilder();

        for(String header : httpServletResponse.getHeaderNames()) {
            String headerValue = httpServletResponse.getHeader(header);
            builder.append("\nHeader " + header + " has value " + headerValue);
        }
        builder.append("\nResponse status is :" + httpServletResponse.getStatus());
        System.out.println(builder.toString());
        LOGGER.info(builder.toString());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logRequest(request);
        filterChain.doFilter(request, response);
        logResponse(response);
    }

    public void destroy() {

    }
}

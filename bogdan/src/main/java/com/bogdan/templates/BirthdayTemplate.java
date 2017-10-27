package com.bogdan.templates;

import org.antlr.stringtemplate.StringTemplate;

import javax.management.Notification;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BirthdayTemplate extends AbstractTemplate {

    public BirthdayTemplate(ServletContext context) {
        super(context);
    }

    @Override
    public String getHtml() {
        ArrayList<StringTemplate> templates = (ArrayList<StringTemplate>)context.getAttribute("templates");
        for(StringTemplate st : templates){
            if(st.getName().equals("birthday")){
                template = st;
                break;
            }
        }
        template.reset();
        template.setAttribute("username", attributes.get("username"));
        template.setAttribute("from", attributes.get("from"));
        return template.toString();
    }
}
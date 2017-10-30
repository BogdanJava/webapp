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

    public BirthdayTemplate(String templateName){
        template = AppStringTemplates.getTemplateByName(templateName);
    }

    BirthdayTemplate(){}

    @Override
    public String getHtml() {
        if(template == null) {
            ArrayList<StringTemplate> templates = (ArrayList<StringTemplate>) context.getAttribute("templates");
            for (StringTemplate st : templates) {
                if (st.getName().equals("birthday")) {
                    template = st;
                    break;
                }
            }
        }
        template.reset();
        template.setAttribute("username", attributes.get("username"));
        template.setAttribute("from", attributes.get("from"));
        return template.toString();
    }

    @Override
    public String getSubject() {
        return "Happy Birthday to You!";
    }

    @Override
    public String getSample() {
        return "Title: Happy b-day to $username$<br>\n" +
                "Hey, $username$, it's like you have a birthday today! Our greetings!<br>\n" +
                "Yours, $from$.\n";
    }
}
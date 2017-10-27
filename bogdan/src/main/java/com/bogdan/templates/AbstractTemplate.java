package com.bogdan.templates;

import org.antlr.stringtemplate.StringTemplate;

import javax.servlet.ServletContext;
import java.util.Map;

public abstract class AbstractTemplate {

    protected ServletContext context;
    protected StringTemplate template;
    protected Map<String, String> attributes;

    public void setAttributes(Map<String, String> map){
        attributes = map;
    }

    public AbstractTemplate(ServletContext context){
        this.context = context;
    }

    public static AbstractTemplate getInstance(String templateName, ServletContext context){
        switch (templateName){
            case "adv": return new AdvTemplate(context);
            case "birthday": return new BirthdayTemplate(context);
            default: return null;
        }
    }

    public abstract String getHtml();
}

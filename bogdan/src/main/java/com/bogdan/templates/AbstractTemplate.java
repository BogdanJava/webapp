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

    AbstractTemplate(){}

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

    public abstract String getSample();

    public abstract String getHtml();

    public abstract String getSubject();

    public static String getTemplateValue(String templateName){
        switch (templateName){
            case "adv" : return new AdvTemplate().getSample();
            case "birthday" : return new BirthdayTemplate().getSample();
            default: return null;
        }
    }
}

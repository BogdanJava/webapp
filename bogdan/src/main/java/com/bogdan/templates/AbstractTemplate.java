package com.bogdan.templates;

import org.antlr.stringtemplate.StringTemplate;

import java.util.Map;

public abstract class AbstractTemplate {
    protected StringTemplate template;
    protected Map<String, String> attributes;

    public void setAttributes(Map<String, String> map){
        attributes = map;
    }

    AbstractTemplate(){}

    public static AbstractTemplate getInstance(String templateName){
        switch (templateName){
            case "adv": return new AdvTemplate();
            case "birthday": return new BirthdayTemplate();
            default: return null;
        }
    }

    public static String getTemplateName(String fileName) {
        switch (fileName) {
            case "adv":
                return "Рекламный";
            case "birthday":
                return "Поздравление с ДР";
            case "newyear":
                return "Поздравление с НГ";
            default:
                return null;
        }
    }

    public abstract String getStandardTopic();

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

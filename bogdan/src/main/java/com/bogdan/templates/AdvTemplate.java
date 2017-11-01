package com.bogdan.templates;

public class AdvTemplate extends AbstractTemplate{

    public AdvTemplate(){}

    @Override
    public String getHtml(){
        template = AppStringTemplates.getTemplateByName("adv");
        template.reset();
        template.setAttribute("title", "Рекламное предложение");
        template.setAttribute("username", attributes.get("username"));
        template.setAttribute("respath", attributes.get("respath"));
        return template.toString();
    }

    @Override
    public String getStandardTopic() {
        return "Adv message";
    }

    @Override
    public String getSubject() {
        return "Adv message";
    }

    @Override
    public String getSample() {
        return "Title: Adv message<br>\n" +
                "Hi, $username$! Try to use this resource.<br>\n" +
                "I'll be glad to see you there!\n";
    }
}

package com.bogdan.templates;

public class BirthdayTemplate extends AbstractTemplate {

    public BirthdayTemplate(){}

    @Override
    public String getHtml() {
        if(template == null) {
            template = AppStringTemplates.getTemplateByName("birthday");
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
    public String getStandardTopic() {
        return "Happy birthday to You!";
    }

    @Override
    public String getSample() {
        return "Title: Happy b-day to $username$<br>\n" +
                "Hey, $username$, it's like you have a birthday today! Our greetings!<br>\n" +
                "Yours, $from$.\n";
    }
}
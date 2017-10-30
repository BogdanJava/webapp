package com.bogdan.templates;

import org.antlr.stringtemplate.StringTemplate;
import javax.servlet.ServletContext;
import java.util.ArrayList;

public class AdvTemplate extends AbstractTemplate{

    AdvTemplate(){}

    public AdvTemplate(ServletContext context){
        super(context);
    }

    @Override
    public String getHtml(){
        ArrayList<StringTemplate> templates = (ArrayList<StringTemplate>)context.getAttribute("templates");
        if(template == null)
        for(StringTemplate st : templates){
            if(st.getName().equals("adv")){
                template = st;
                break;
            }
        }
        template.reset();
        template.setAttribute("title", "Рекламное предложение");
        template.setAttribute("username", attributes.get("username"));
        template.setAttribute("respath", attributes.get("respath"));
        return template.toString();
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

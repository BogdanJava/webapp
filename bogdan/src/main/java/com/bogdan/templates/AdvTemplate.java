package com.bogdan.templates;

import com.bogdan.logic.MailUtils;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvTemplate extends AbstractTemplate{

    public AdvTemplate(ServletContext context){
        super(context);
    }

    @Override
    public String getHtml(){
        ArrayList<StringTemplate> templates = (ArrayList<StringTemplate>)context.getAttribute("templates");
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
}

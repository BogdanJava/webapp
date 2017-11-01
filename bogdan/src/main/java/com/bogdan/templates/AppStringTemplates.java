package com.bogdan.templates;

import org.antlr.stringtemplate.StringTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppStringTemplates {
    private static Map<String, StringTemplate> mapper = new HashMap<>();

    public static void initMapper(ArrayList<StringTemplate> list){
            for(StringTemplate template : list){
                mapper.put(template.getName(), template);
            }
    }

    public static StringTemplate getTemplateByName(String name){
        return mapper.get(name);
    }

}

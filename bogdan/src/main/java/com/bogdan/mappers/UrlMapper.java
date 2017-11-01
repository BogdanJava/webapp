package com.bogdan.mappers;

import java.util.HashMap;
import java.util.Map;
/*класс предназначен для возвращения команды исходя из URL'а, если параметр command не был
передан вместе с формой*/
public class UrlMapper {
    private static Map<String, String> pathProcessor = new HashMap<>();
    static {
        pathProcessor.put("/add", "viewAddContact");
        pathProcessor.put("/contacts", "viewContacts");
        pathProcessor.put("/edit", "viewEdit");
        pathProcessor.put("/search", "viewSearch");
        pathProcessor.put("/sendMail", "viewSend");
        pathProcessor.put("/", "viewContacts");
    }
    public static String getCommandName(String pathInfo){
        return pathProcessor.get(pathInfo);
    }
}

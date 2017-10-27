package com.bogdan.viewhelper;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private static Map<String, String> pathProcessor = new HashMap<>();
    static {
        pathProcessor.put("/add", "viewAddContact");
        pathProcessor.put("/welcome", "viewWelcome");
        pathProcessor.put("/contacts", "viewContacts");
        pathProcessor.put("/edit", "viewEdit");
        pathProcessor.put("/search", "viewSearch");
        pathProcessor.put("/sendMail", "viewSend");
        pathProcessor.put("/", "viewWelcome");
    }
    public static String getCommandName(String pathInfo){
        return pathProcessor.get(pathInfo);
    }
}

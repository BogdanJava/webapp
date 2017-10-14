package com.bogdan.viewhelper;

import com.bogdan.logic.ProcessAddContact;

import java.util.HashMap;
import java.util.Map;

public class BasicCommandMapper extends CommandMapper {
    private Map<String, Class> processors = new HashMap<>();
    {
        processors.put("viewAddContact", AddContactPageViewHelper.class);
        processors.put("viewWelcome", WelcomePageViewHelper.class);
        processors.put("viewContacts", ShowContactsViewHelper.class);
        processors.put("processAddContact", ProcessAddContact.class);
        processors.put(null, WelcomePageViewHelper.class);
    }
    @Override
    protected Map<String, Class> getProcessors() {
        return processors;
    }
}

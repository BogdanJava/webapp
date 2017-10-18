package com.bogdan.viewhelper;

import com.bogdan.logic.ProcessAddContact;
import com.bogdan.logic.ProcessDeleteContacts;
import com.bogdan.logic.ProcessModifyContact;
import com.bogdan.logic.ProcessSearchContacts;

import java.util.HashMap;
import java.util.Map;

public class BasicCommandMapper extends CommandMapper {
    private Map<String, Class<? extends Command>> processors = new HashMap<>();
    {
        processors.put("viewAddContact", AddContactPageViewHelper.class);
        processors.put("viewEdit", ModifyContactViewHelper.class);
        processors.put("viewWelcome", WelcomePageViewHelper.class);
        processors.put("viewContacts", ShowContactsViewHelper.class);
        processors.put("viewSearch", SearchViewHelper.class);
        processors.put("processAddContact", ProcessAddContact.class);
        processors.put("processDeleteContacts", ProcessDeleteContacts.class);
        processors.put("processModifyContact", ProcessModifyContact.class);
        processors.put("processSearchContacts", ProcessSearchContacts.class);
        processors.put(null, WelcomePageViewHelper.class);
    }
    @Override
    protected Map<String, Class<? extends Command>> getProcessors() {
        return processors;
    }
}

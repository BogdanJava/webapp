package com.bogdan.mappers;

import com.bogdan.commands.Command;
import com.bogdan.commands.actions.*;
import com.bogdan.commands.showpage.*;

import java.util.HashMap;
import java.util.Map;

public class BasicCommandMapper extends CommandMapper {
    private Map<String, Class<? extends Command>> processors = new HashMap<>();
    {
        processors.put("viewAddContact", ShowAddPageCommand.class);
        processors.put("viewEdit", ShowEditPageCommand.class);
        processors.put("viewContacts", ShowContactsCommand.class);
        processors.put("viewSearch", ShowFindPageCommand.class);
        processors.put("viewSend", ShowSendMailPageCommand.class);
        processors.put("processAddContact", AddCommand.class);
        processors.put("processDeleteContacts", DeleteCommand.class);
        processors.put("processModifyContact", EditCommand.class);
        processors.put("processSearchContacts", FindCommand.class);
        processors.put("processSendMail", SendMailCommand.class);
        processors.put(null, ShowContactsCommand.class);
    }
    @Override
    protected Map<String, Class<? extends Command>> getProcessors() {
        return processors;
    }
}

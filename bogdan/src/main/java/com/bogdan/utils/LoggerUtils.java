package com.bogdan.utils;

import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Phone;
import com.bogdan.pojo.Row;

public class LoggerUtils {

    public static String getInsertLog(Row row) {
        return ("Contact inserted: " + row.getContact() + "\n") +
                "Phones inserted: " + row.getPhones() + "\n" +
                "Number of attached files: " + row.getFiles().size() + "\n";
    }

    public static String getEditLog(Row deletedRow, Row insertedRow) {
        StringBuilder builder = new StringBuilder();
        if(deletedRow != null) {
            if(deletedRow.getPhones() != null)
            for (Phone phone : deletedRow.getPhones())
                builder.append("Phone #" + phone.getId() + "of contact #"
                        + deletedRow.getContact().getId() + " deleted");
            if(deletedRow.getFiles() != null)
            for (AttachedFile file : deletedRow.getFiles())
                builder.append("File #" + file.getId() + "of contact #" +
                        deletedRow.getContact().getId() + " deleted");
            builder.append("Contact #" + deletedRow.getContact().getId() + " updated: "
                    + deletedRow.getContact());
        }
        if(insertedRow != null) {
            if(insertedRow.getFiles() != null)
            for (AttachedFile file : insertedRow.getFiles())
                builder.append("New file " + file + " inserted");
            if(insertedRow.getPhones() != null)
            for (Phone phone : insertedRow.getPhones())
                builder.append("New phone " + phone + " inserted");
        }
        return builder.toString();
    }

}

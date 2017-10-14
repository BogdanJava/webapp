package com.bogdan.logic;

import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class LogicUtils {
    private static final Logger LOGGER = Logger.getLogger("user_action_logger");
    {
        if(!new File(AttachedFile.UPLOADPATH).exists()){
            new File(AttachedFile.UPLOADPATH).mkdir();
        }
    }
    public static boolean createFile(Contact contact, AttachedFile file) {
        String dirPath = AttachedFile.UPLOADPATH + contact.getId();

        if(!new File(dirPath).exists()){
            new File(dirPath).mkdir();
        }
        String tempPath = dirPath + File.separator + file.getName() + file.getType();
        String fileName = file.getName() + file.getType();
        File f = new File(tempPath);
        int i = 0;
        while(f.exists()){
            i++;
            tempPath = dirPath + File.separator + file.getName() + "(" + i + ")" + file.getType();
            fileName = file.getName() + "(" + i + ")" + file.getType();
            f = new File(tempPath);
        }
        file.setRealPath(tempPath);
        file.setRelativePath(AttachedFile.RELATIVEPATH + contact.getId() + File.separator + fileName);
        FileOutputStream fos = null;
        try {
            if (f.createNewFile()) {
                fos = new FileOutputStream(f);
                fos.write(file.getBytes());
                return true;
            } else {
                LOGGER.error("Cannot create file " + file.getRealPath());
                return false;
            }
        } catch(IOException e){
            LOGGER.error("Exception while creating file: " + e);
            return false;
        }
        finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<AttachedFile> initFiles(HttpServletRequest req, int contactId){
        ArrayList<AttachedFile> files = (ArrayList<AttachedFile>)req.getAttribute("fileList");
        for(AttachedFile file : files){
            file.setContactId(contactId);
        }
        return files;
    }

    public static ArrayList<Phone> initPhones(HttpServletRequest req, int contactId){
        ArrayList<Phone> phones = (ArrayList<Phone>)req.getAttribute("phoneList");
        for(Phone phone : phones){
            phone.setContactId(contactId);
        }
        return phones;
    }

    public static Contact initContact(HttpServletRequest req) throws ParseException {
        Contact c = new Contact();
        c.setFirstName((String)req.getAttribute("firstName"));
        c.setLastName((String)req.getAttribute("lastName"));
        c.setPatronymic((String)req.getAttribute("patronymic"));
        String year = (String)req.getAttribute("year");
        String month =(String)req.getAttribute("month");
        String day = (String)req.getAttribute("day");
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = df.parse(year + "-" + month + "-" + day);
        c.setBirthDate(date);
        c.setGender((String)req.getAttribute("gender"));
        c.setMaritalStatus((String)req.getAttribute("marital"));
        c.setWebsiteURL((String)req.getAttribute("url"));
        c.setJobPlace((String)req.getAttribute("job"));
        c.setState((String)req.getAttribute("country"));
        c.setCity((String)req.getAttribute("city"));
        c.setPostalCode((String)req.getAttribute("index"));
        c.setStreet((String)req.getAttribute("street"));
        c.setHouseNumber((String)req.getAttribute("house_number"));
        c.setEmail((String)req.getAttribute(("email")));
        c.setPhoto((AttachedFile)req.getAttribute("profile_photo"));
        c.setComment((String)req.getAttribute("comment"));
        c.getPhoto().setType(".jpg");
        LogicUtils.createFile(c, c.getPhoto());
        return c;
    }

    public static String getFileType(String fileName){
        String type = null;
        for(int i = fileName.length() - 1; i > 0; i--){
            if(fileName.charAt(i) == '.'){
                type = new String(fileName.substring(i, fileName.length()));
                break;
            }
        }
        return type;
    }

}

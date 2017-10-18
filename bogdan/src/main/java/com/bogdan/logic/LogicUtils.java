package com.bogdan.logic;

import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;
import com.bogdan.pojo.Row;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Enumeration;

public class LogicUtils {
    private static final Logger LOGGER = Logger.getLogger("user_action_logger");
    static {
        if(!new File(AttachedFile.UPLOADPATH).exists()){
            new File(AttachedFile.UPLOADPATH).mkdir();
        }
    }

    public static boolean createFile(Contact contact, AttachedFile file) {

        String dirPath = AttachedFile.UPLOADPATH + contact.getEmail();

        if(!new File(dirPath).exists()){
            new File(dirPath).mkdir();
        }

        String tempPath = dirPath + File.separator + file.getName() + file.getType();
        String fileName = file.getName() + file.getType();
        File f = new File(tempPath);
        int i = 0;
        while(f.exists()){
            i++;
            fileName = file.getName() + "(" + i + ")" + file.getType();
            tempPath = dirPath + File.separator + fileName;
            f = new File(tempPath);
        }
        file.setRealPath(tempPath);
        file.setRelativePath(AttachedFile.RELATIVEPATH + contact.getEmail() + File.separator + fileName);
        FileOutputStream fos = null;
        try {
            if (f.createNewFile()) {
                fos = new FileOutputStream(f);
                fos.write(ArrayUtils.toPrimitive(file.getBytes()));
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
        if(files != null) {
            for (AttachedFile file : files) {
                file.setContactId(contactId);
            }
            return files;
        } else return null;
    }

    public static ArrayList<Phone> initPhones(HttpServletRequest req, int contactId){
        ArrayList<Phone> phones = (ArrayList<Phone>)req.getAttribute("phoneList");
        LOGGER.info(phones);
        if(phones != null) {
            for (Phone phone : phones) {
                phone.setContactId(contactId);
            }
            return phones;
        }
        else return null;
    }

    public static Contact initContact(HttpServletRequest req) throws ParseException {
        Contact c = new Contact();
        c.setFirst_name((String)req.getAttribute("first_name"));
        c.setLast_name((String)req.getAttribute("last_name"));
        c.setPatronymic((String)req.getAttribute("patronymic"));
        String year = (String)req.getAttribute("year");
        String month =(String)req.getAttribute("month");
        String day = (String)req.getAttribute("day");
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = df.parse(year + "-" + month + "-" + day);
        c.setBirthDate(date);
        c.setGender((String)req.getAttribute("gender"));
        c.setMarital_status((String)req.getAttribute("marital"));
        c.setWebsite_url((String)req.getAttribute("url"));
        c.setJob_place((String)req.getAttribute("job"));
        c.setState((String)req.getAttribute("country"));
        c.setCity((String)req.getAttribute("city"));
        c.setPostal_code((String)req.getAttribute("index"));
        c.setStreet((String)req.getAttribute("street"));
        c.setHouse_number((String)req.getAttribute("house_number"));
        c.setEmail((String)req.getAttribute(("email")));
        c.setPhoto((AttachedFile)req.getAttribute("profile_photo"));
        c.setComment((String)req.getAttribute("comment"));
        if(c.getPhoto() != null)
        c.getPhoto().setType(".jpg");
        return c;
    }

    public static ArrayList<Contact> getContactFromResultSet(ResultSet lines) throws SQLException {
        ArrayList<Contact> list = new ArrayList<>();
        while(lines.next()){
            Contact c = new Contact();
            c.setId(lines.getInt("id"));
            c.setFirst_name(lines.getString("first_name"));
            c.setLast_name(lines.getString("last_name"));
            c.setPatronymic(lines.getString("patronymic"));
            c.setMarital_status(lines.getString("marital_status"));
            c.setBirthDate(lines.getDate("date_of_birth"));
            c.setState(lines.getString("state"));
            c.setGender(lines.getString("gender"));
            c.setCity(lines.getString("city"));
            c.setComment(lines.getString("comment"));
            c.setEmail(lines.getString("email"));
            c.setHouse_number(lines.getString("house_number"));
            c.setJob_place(lines.getString("job_place"));
            AttachedFile af = new AttachedFile();
            af.setRelativePath(lines.getString("photo_url"));
            af.setRealPath(getAbsoluteOfRelative(af.getRelativePath()));
            c.setPhoto(af);
            c.setPostal_code(lines.getString("postal_code"));
            c.setStreet(lines.getString("street"));
            c.setWebsite_url(lines.getString("website_url"));
            list.add(c);
        }
        return list;
    }

    public static ArrayList<Phone> getPhonesFromResultSet(ResultSet lines) throws SQLException{
        ArrayList<Phone> list = new ArrayList<>();

        while(lines.next()){
            Phone p = new Phone();
            p.setId(lines.getInt("id"));
            p.setComment(lines.getString("comment"));
            p.setContactId(lines.getInt("contact_id"));
            p.setNumber(lines.getString("number"));
            p.setOperatorCode(lines.getString("operator_code"));
            p.setStateCode(lines.getString("state_code"));
            p.setType(lines.getString("phone_type"));
            list.add(p);
        }
        if(list.size() > 0){
            return list;
        }
        else return null;
    }

    public static ArrayList<AttachedFile> getFilesFromResultSet(ResultSet lines) throws SQLException {
        ArrayList<AttachedFile> list = new ArrayList<>();

        while(lines.next()){
            AttachedFile file = new AttachedFile();
            file.setId(lines.getInt("id"));
            file.setRelativePath(lines.getString("relative_path"));
            file.setRealPath(lines.getString("real_path"));
            file.setName(lines.getString("name"));
            file.setContactId(lines.getInt("contact_id"));
            file.setType(lines.getString("file_type"));
            list.add(file);
        }
        if(list.size() > 0){
            return list;
        }
        else return null;
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

    public static Integer[] parseStringToInt(String[] arr){
        Integer[] array = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++){
            array[i] = Integer.parseInt(arr[i]);
        }
        return array;
    }

    public static void parseParameters(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] values;

            values = request.getParameterValues(paramName);
            if(values.length == 1) request.setAttribute(paramName, values[0]);
            else request.setAttribute(paramName, values);
        }
    }

    public static String getAbsoluteOfRelative(String relativePath){
        return System.getProperty("catalina.base") + File.separator + "webapps" + relativePath;
    }

    public static ArrayList<Row> getAllRows() throws SQLException{
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        MysqlFileDAO fileDAO = new MysqlFileDAO();
        MysqlPhoneDAO phoneDAO = new MysqlPhoneDAO();
        ArrayList<Row> rows = null;
        ArrayList<Contact> contacts = contactDAO.getAll();
        rows = new ArrayList<>(contacts.size());

        for (int i=0; i<contacts.size(); i++) {
            ArrayList<Phone> phones = phoneDAO.getAll(contacts.get(i).getId());
            ArrayList<AttachedFile> files = fileDAO.getAll(contacts.get(i).getId());
            rows.add(new Row(contacts.get(i), phones, files));
        }
        return rows;
    }

    public static <T> void initLists(Field[] fields, ArrayList<Field> notNullFields, ArrayList<Object> values, T data) throws IllegalAccessException {
        for(int i=0; i<fields.length; i++){
            fields[i].setAccessible(true);
            if(fields[i].get(data) != null){
                notNullFields.add(fields[i]);
                values.add(fields[i].get(data));
            }
        }
    }
    public static String getQuery(String sqlString, ArrayList<Field> notNullFields, ArrayList<Object> values){

        StringBuilder sql = new StringBuilder(sqlString);
        String fieldName, forAppend;
        Object fieldValue;

        for(int i=0; i<notNullFields.size(); i++) {
            fieldName = notNullFields.get(i).getName();
            fieldValue = values.get(i);
            if (notNullFields.get(i).getType() != int.class && notNullFields.get(i).getType() != Integer.class) {
                forAppend = " " + fieldName + " LIKE '" + fieldValue + "%'";
            } else {
                forAppend = String.format(" %s = %d", fieldName, (int) fieldValue);
            }
            if (i < notNullFields.size() - 1) {
                forAppend += " AND";
            } else forAppend += ";";
            sql.append(forAppend);
        }
        return sql.toString();
    }
}
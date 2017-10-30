package com.bogdan.logic;

import com.bogdan.dao.GenericDAO;
import com.bogdan.dao.MysqlContactDAO;
import com.bogdan.dao.MysqlFileDAO;
import com.bogdan.dao.MysqlPhoneDAO;
import com.bogdan.pojo.*;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogicUtils {
    private static final Logger LOGGER = Logger.getLogger("user_action_logger");
    static {
        if(!new File(AttachedFile.UPLOADPATH).exists()){
            new File(AttachedFile.UPLOADPATH).mkdir();
        }
    }

    public static void initTemplates(ServletContext context){
        if(context.getAttribute("templates") == null) {
            StringTemplateGroup stGroup = new StringTemplateGroup("group",
                    LogicUtils.getAbsoluteOfRelative(context.getContextPath()) +
                            "\\mailtemplates", DefaultTemplateLexer.class);

            ArrayList<StringTemplate> templates = new ArrayList<>();
            templates.add(stGroup.getInstanceOf("adv"));
            templates.add(stGroup.getInstanceOf("birthday"));
            context.setAttribute("templates", templates);
        }
    }

    public static String getTemplateName(String fileName){
        switch(fileName){
            case "adv": return "Рекламный";
            case "birthday": return "Поздравление с ДР";
            case "newyear": return "Поздравление с НГ";
            default: return null;
        }
    }

    public static boolean createFile(Contact contact, AttachedFile file) {

        if(contact == null || file == null){
            return false;
        }
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
                file.setDate(new Date());
            }
            return files;
        } else return null;
    }

    public static ArrayList<Contact> getTodayBirthdayMan(Contact contact){
        MysqlContactDAO contactDao = new MysqlContactDAO();
        ArrayList<Contact> contacts = null;
        try {
            contacts = contactDao.getBirthdays(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
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
        c.setFirstName((String)req.getAttribute("first_name"));
        c.setLastName((String)req.getAttribute("last_name"));
        c.setPatronymic((String)req.getAttribute("patronymic"));
        String year = (String)req.getAttribute("year");
        String month =(String)req.getAttribute("month");
        String day = (String)req.getAttribute("day");
        if (year != null && month != null && day != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(year + "-" + month + "-" + day);
            c.setBirthDate(date);
        }
        LOGGER.info(c.getBirthDate());
        c.setGender((String)req.getAttribute("gender"));
        c.setMaritalStatus((String)req.getAttribute("marital"));
        c.setWebsiteUrl((String)req.getAttribute("url"));
        c.setJobPlace((String)req.getAttribute("job"));
        c.setState((String)req.getAttribute("country"));
        c.setCity((String)req.getAttribute("city"));
        c.setPostalCode((String)req.getAttribute("index"));
        c.setStreet((String)req.getAttribute("street"));
        c.setHouseNumber((String)req.getAttribute("house_number"));
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
            c.setFirstName(lines.getString("first_name"));
            c.setLastName(lines.getString("last_name"));
            c.setPatronymic(lines.getString("patronymic"));
            c.setMaritalStatus(lines.getString("marital_status"));
            c.setBirthDate(lines.getDate("date_of_birth"));
            c.setState(lines.getString("state"));
            c.setGender(lines.getString("gender"));
            c.setCity(lines.getString("city"));
            c.setComment(lines.getString("comment"));
            c.setEmail(lines.getString("email"));
            c.setHouseNumber(lines.getString("house_number"));
            c.setJobPlace(lines.getString("job_place"));
            AttachedFile af = new AttachedFile();
            af.setRelativePath(lines.getString("photo_url"));
            af.setRealPath(getAbsoluteOfRelative(af.getRelativePath()));
            c.setPhoto(af);
            c.setPostalCode(lines.getString("postal_code"));
            c.setStreet(lines.getString("street"));
            c.setWebsiteUrl(lines.getString("website_url"));
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
            file.setDate(lines.getDate("add_date"));
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

        LOGGER.info(contacts);
        for (int i=0; i<contacts.size(); i++) {
            ArrayList<Phone> phones = phoneDAO.getAll(contacts.get(i).getId());
            ArrayList<AttachedFile> files = fileDAO.getAll(contacts.get(i).getId());
            rows.add(new Row(contacts.get(i), phones, files));
        }
        return rows;
    }

    public static ArrayList<Row> search(Contact criteria, Limit limit) throws SQLException {
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        MysqlFileDAO fileDAO = new MysqlFileDAO();
        MysqlPhoneDAO phoneDAO = new MysqlPhoneDAO();
        ArrayList<Row> rows = new ArrayList<>();

        ArrayList<Contact> contacts = contactDAO.find(criteria, limit);
        for(Contact c : contacts){
            ArrayList<Phone> phones = phoneDAO.find(new Phone(c.getId()), null);
            ArrayList<AttachedFile> files = fileDAO.find(new AttachedFile(c.getId()), null);
            rows.add(new Row(c, phones, files));
        }
        return rows;
    }

    public static ArrayList<Row> getLimitedRows(int from, int number) throws SQLException{
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        MysqlFileDAO fileDAO = new MysqlFileDAO();
        MysqlPhoneDAO phoneDAO = new MysqlPhoneDAO();
        ArrayList<Row> rows = new ArrayList<>();

        ArrayList<Contact> contacts = contactDAO.getLimitedContacts(from, number);
        for(Contact c : contacts){
            ArrayList<Phone> phones = phoneDAO.getLimited(from, number, c.getId());
            ArrayList<AttachedFile> files = fileDAO.getLimited(from, number, c.getId());
            rows.add(new Row(c, phones, files));
        }
        return rows;
    }

    public static <T> void initLists(Field[] fields, ArrayList<Field> notNullFields, ArrayList<Object> values, T data) {
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].get(data) != null && !fields[i].get(data).equals("") && !Modifier.isStatic(fields[i].getModifiers())) {
                    notNullFields.add(fields[i]);
                    values.add(fields[i].get(data));
                }
            }
        } catch(IllegalAccessException e){
            LOGGER.info(e.getMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.info(el);
            }
        }
    }
    public static String getQuery(String sqlString, ArrayList<Field> notNullFields, ArrayList<Object> values){

        StringBuilder sql = new StringBuilder(sqlString);
        Integer fieldCount = notNullFields.size();

        for(int i=0; i<fieldCount; i++){
            String forAppend = " AND ";
            Field currField = notNullFields.get(i);
            LOGGER.info("name: " + currField.getName()  + "; " +
                    "type: " + currField.getType() + "; value: " + values.get(i));
            if(currField.getType() == String.class){
                forAppend += String.format("%s LIKE ?", currField.getName());
            }
            if(currField.getType() == Integer.class){
                forAppend += String.format("%s = ?", currField.getName());
            }
            if(currField.getType() == java.util.Date.class){
                if(currField.getName().equals("date_of_birth")){
                    forAppend += String.format("%s = ?", currField.getName());
                }
                else continue;
            }
            if(!forAppend.equals(" AND "))
                sql.append(forAppend);
        }
        return sql.toString();
    }

    public static void initStatement(PreparedStatement statement, ArrayList<Field> notNullFields,
                                     ArrayList<Object> values) throws SQLException {
        for(int i=0; i < notNullFields.size(); i++){
            Field currField = notNullFields.get(i);
            Object currValue = values.get(i);

            if(currField.getType() == Integer.class){
                statement.setInt(i+1, (Integer)currValue);
            }
            if(currField.getType() == String.class){
                statement.setString(i+1, (String)currValue + "%");
            }
            if(currField.getType() == java.util.Date.class){
                java.util.Date date = (java.util.Date) currValue;
                java.sql.Date d = LogicUtils.convertJavaDateToSqlDate(date);
                statement.setDate(i+1, d);
            }
        }
    }

    public static ArrayList<Contact> getContactsById(HttpServletRequest req) throws SQLException {
        GenericDAO<Contact> contactDAO = new MysqlContactDAO();
        ArrayList<Contact> list = new ArrayList<>();
        String[] emails = req.getParameterValues("emails");
        if(emails == null) emails = new String[]{};
        for(String emailId : emails){
            list.add(contactDAO.find(new Contact(Integer.parseInt(emailId)),null).get(0));
        }
        return list;
    }

    public static ArrayList<Phone> getPhonesByContact(Contact contact) throws  SQLException {
        GenericDAO<Phone> phoneDAO = new MysqlPhoneDAO();
        return phoneDAO.find(new Phone(contact.getId()),null);
    }

    public static ArrayList<AttachedFile> getFilesByContact(Contact contact) throws  SQLException {
        GenericDAO<AttachedFile> fileDAO = new MysqlFileDAO();
        return fileDAO.find(new AttachedFile(contact.getId()),null);
    }

    public static void setDateRange(HttpServletRequest req, Contact c){
        String dateFrom = req.getParameter("less_day") != null && !req.getParameter("less_day").equals("")
                ? req.getParameter("less_day") : "01";
        String monthFrom = req.getParameter("less_month") != null && !req.getParameter("less_month").equals("")
                ? req.getParameter("less_month") : "01";
        String yearFrom = req.getParameter("less_year") != null && !req.getParameter("less_year").equals("")
                ? req.getParameter("less_year") : "1753";
        Calendar calendar = Calendar.getInstance();
        String dateTo = req.getParameter("more_day") != null && !req.getParameter("more_day").equals("")
                ? req.getParameter("more_day") : String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String monthTo = req.getParameter("more_month") != null && !req.getParameter("more_month").equals("")
                ? req.getParameter("more_month") : String.valueOf(calendar.get(Calendar.MONTH));
        String yearTo = req.getParameter("more_year") != null && !req.getParameter("more_year").equals("")
                ? req.getParameter("more_year") : String.valueOf(calendar.get(Calendar.YEAR));

        if(Integer.parseInt(monthTo) < 10) monthTo = "0" + monthTo;
        c.setDateFrom(new Date(Integer.parseInt(yearFrom) - 1900, Integer.parseInt(monthFrom) - 1,
                Integer.parseInt(dateFrom)));
        c.setDateTo(new Date(Integer.parseInt(yearTo) - 1900, Integer.parseInt(monthTo),
                Integer.parseInt(dateTo)));
    }
    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    public static Integer getContactsNumber(Contact criteria) throws SQLException {
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        return contactDAO.getContactsNumber(criteria);
    }

    public static boolean hasCriteria(HttpServletRequest req){
        Integer deleteFilter = 0;
        LOGGER.info(req.getAttribute("filterDelete") != null);
        if(req.getAttribute("filterDelete") != null)
            deleteFilter = Integer.parseInt((String)req.getAttribute("filterDelete"));

        LOGGER.info(deleteFilter);
        if(deleteFilter == 1) {
            LOGGER.info("removed");
            req.getSession().removeAttribute("criteria");
            LOGGER.info(req.getSession().getAttributeNames());
        }
        return req.getSession().getAttribute("criteria") != null;
    }

}
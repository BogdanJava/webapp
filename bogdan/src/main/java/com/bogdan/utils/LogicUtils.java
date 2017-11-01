package com.bogdan.utils;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

public class LogicUtils {
    private static final Logger LOGGER = Logger.getLogger("user_action_logger");

    private GenericDAO<Contact> contactDao = new MysqlContactDAO();
    private GenericDAO<Phone> phoneDao = new MysqlPhoneDAO();
    private GenericDAO<AttachedFile> fileDao = new MysqlFileDAO();

    static {
        if (!new File(AttachedFile.UPLOADPATH).exists()) {
            new File(AttachedFile.UPLOADPATH).mkdir();
        }
    }

    public static void prepareContact(Contact contact){

    }

    public static void initTemplates(ServletContext context) {
        if (context.getAttribute("templates") == null) {
            StringTemplateGroup stGroup = new StringTemplateGroup("group",
                    LogicUtils.getAbsoluteOfRelative(context.getContextPath()) +
                            "\\mailtemplates", DefaultTemplateLexer.class);

            ArrayList<StringTemplate> templates = new ArrayList<>();
            templates.add(stGroup.getInstanceOf("adv"));
            templates.add(stGroup.getInstanceOf("birthday"));
            context.setAttribute("templates", templates);
        }
    }

    public static String getTemplateName(String fileName) {
        switch (fileName) {
            case "adv":
                return "Рекламный";
            case "birthday":
                return "Поздравление с ДР";
            case "newyear":
                return "Поздравление с НГ";
            default:
                return null;
        }
    }

    public static String[] getEmails(HttpServletRequest req){
        String[] values;
        Object attribute = req.getAttribute("emailaddresses");
        if(attribute instanceof String[]) values = (String[])attribute;
        else {
            values = new String[1];
            values[0] = (String)attribute;
        }
        return values;
    }



    public static StringTemplate getSelectedTemplate(HttpServletRequest req){
        ArrayList<StringTemplate> templates = (ArrayList<StringTemplate>) req.getServletContext().getAttribute("templates");
        StringTemplate template = null;
        for (StringTemplate t : templates) {
            if (t.getName().equals((String) req.getAttribute("template"))) {
                template = t;
                break;
            }
        }
        return template;
    }

    public static boolean createFile(Contact contact, AttachedFile file) throws IOException {

        if (contact == null || file == null) {
            return false;
        }
        String dirPath = AttachedFile.UPLOADPATH + contact.getEmail();

        if (!new File(dirPath).exists()) {
            if(!new File(dirPath).mkdir()) return false;
        }

        String tempPath = dirPath + File.separator + file.getName() + file.getType();
        String fileName = file.getName() + file.getType();
        File f = new File(tempPath);
        int i = 0;
        while (f.exists()) {
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
        } finally {
            if (fos != null) fos.close();
        }
    }

    public static ArrayList<AttachedFile> initFiles(HttpServletRequest req, int contactId) {
        ArrayList<AttachedFile> files = (ArrayList<AttachedFile>) req.getAttribute("fileList");
        if (files != null) {
            for (AttachedFile file : files) {
                file.setContactId(contactId);
                file.setDate(new Date());
            }
            return files;
        } else return null;
    }

    public static ArrayList<Phone> initPhones(HttpServletRequest req, int contactId) {
        ArrayList<Phone> phones = (ArrayList<Phone>) req.getAttribute("phoneList");
        if (phones != null) {
            for (Phone phone : phones) {
                phone.setContactId(contactId);
            }
            return phones;
        } else return null;
    }

    public static Contact initContact(HttpServletRequest req) throws ParseException {
        Contact c = new Contact();
        String id = (String)req.getAttribute("editContactId");
        if(id != null) c.setId(Integer.parseInt(id));
        c.setFirstName((String) req.getAttribute("first_name"));
        c.setLastName((String) req.getAttribute("last_name"));
        c.setPatronymic((String) req.getAttribute("patronymic"));
        String year = (String) req.getAttribute("year");
        String month = (String) req.getAttribute("month");
        String day = (String) req.getAttribute("day");
        if (year != null && month != null && day != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(year + "-" + month + "-" + day);
            c.setBirthDate(date);
        }
        c.setGender((String) req.getAttribute("gender"));
        c.setMaritalStatus((String) req.getAttribute("marital"));
        c.setWebsiteUrl((String) req.getAttribute("url"));
        c.setJobPlace((String) req.getAttribute("job"));
        c.setState((String) req.getAttribute("country"));
        c.setCity((String) req.getAttribute("city"));
        c.setPostalCode((String) req.getAttribute("index"));
        c.setStreet((String) req.getAttribute("street"));
        c.setHouseNumber((String) req.getAttribute("house_number"));
        c.setEmail((String) req.getAttribute(("email")));
        boolean updatePhoto = req.getAttribute("notUpdate") == null;
        if(updatePhoto) {
            LOGGER.info("updatePhoto");
            c.setPhoto((AttachedFile) req.getAttribute("profile_photo"));
        }
        c.setComment((String) req.getAttribute("comment"));
        if (c.getPhoto() != null)
            c.getPhoto().setType(".jpg");
        return c;
    }

    public static ArrayList<Contact> getContactFromResultSet(ResultSet lines) throws SQLException {
        ArrayList<Contact> list = new ArrayList<>();
        while (lines.next()) {
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

    public static ArrayList<Phone> getPhonesFromResultSet(ResultSet lines) throws SQLException {
        ArrayList<Phone> list = new ArrayList<>();

        while (lines.next()) {
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
        if (list.size() > 0) {
            return list;
        } else return null;
    }

    public static ArrayList<AttachedFile> getFilesFromResultSet(ResultSet lines) throws SQLException {
        ArrayList<AttachedFile> list = new ArrayList<>();

        while (lines.next()) {
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
        if (list.size() > 0) {
            return list;
        } else return null;
    }

    public static String getFileType(String fileName) {
        String type = null;
        for (int i = fileName.length() - 1; i > 0; i--) {
            if (fileName.charAt(i) == '.') {
                type = fileName.substring(i, fileName.length());
                break;
            }
        }
        return type;
    }

    public static Integer[] parseStringToInt(String[] arr) {
        Integer[] array = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
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
            if (values.length == 1) request.setAttribute(paramName, values[0]);
            else request.setAttribute(paramName, values);
        }
    }

    public static String getAbsoluteOfRelative(String relativePath) {
        return System.getProperty("catalina.base") + File.separator + "webapps" + relativePath;
    }

    public static ArrayList<Row> search(Contact criteria, Limit limit) throws SQLException {
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        MysqlFileDAO fileDAO = new MysqlFileDAO();
        MysqlPhoneDAO phoneDAO = new MysqlPhoneDAO();
        ArrayList<Row> rows = new ArrayList<>();

        ArrayList<Contact> contacts = contactDAO.find(criteria, limit);
        for (Contact c : contacts) {
            ArrayList<Phone> phones = phoneDAO.find(new Phone(c.getId()), null);
            ArrayList<AttachedFile> files = fileDAO.find(new AttachedFile(c.getId()), null);
            rows.add(new Row(c, phones, files));
        }
        return rows;
    }

    public static ArrayList<Row> getLimitedRows(int from, Contact criteria) throws SQLException {
        ArrayList<Row> rows;
        if(criteria != null){
            rows = LogicUtils.search(criteria, new Limit(from, 10));
        } else{
            rows = LogicUtils.search(new Contact(), new Limit(from, 10));
        }
        if(rows.size() == 0) {
            rows = LogicUtils.search(criteria, new Limit(0, 10));
        }
        return rows;
    }

    public static Integer getPageId(HttpServletRequest req) throws SQLException {
        String page = req.getParameter("page");
        Integer pageId;

        if(page != null){
            if(!page.equals("") && page.matches("^[0-9]+$")) {
                pageId = Integer.parseInt(page);
                if(pageId <= 0 || pageId > LogicUtils.getContactsNumber(new Contact()) / 10 + 1){
                    return null;
                }
            } else return null;
        }
        else return null;

        return pageId;

    }

    public static Contact getCriteria(HttpServletRequest req){
        Contact criteria = (Contact)req.getSession().getAttribute("criteria");
        if(criteria != null) return criteria;
        else return new Contact();
    }

    public static <T> void initLists(Field[] fields, ArrayList<Field> notNullFields, ArrayList<Object> values, T data) {
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(data) != null && !field.get(data).equals("") && !Modifier.isStatic(field.getModifiers())) {
                    notNullFields.add(field);
                    values.add(field.get(data));
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.info(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                LOGGER.info(el);
            }
        }
    }

    public static String getQuery(String sqlString, ArrayList<Field> notNullFields) {

        StringBuilder sql = new StringBuilder(sqlString);

        for (Field notNullField : notNullFields) {
            String forAppend = " AND ";
            if (notNullField.getType() == String.class) {
                forAppend += String.format("%s LIKE ?", notNullField.getName());
            }
            if (notNullField.getType() == Integer.class) {
                forAppend += String.format("%s = ?", notNullField.getName());
            }
            if (notNullField.getType() == Date.class) {
                if (notNullField.getName().equals("date_of_birth")) {
                    forAppend += String.format("%s = ?", notNullField.getName());
                } else continue;
            }
            if (!forAppend.equals(" AND "))
                sql.append(forAppend);
        }
        return sql.toString();
    }

    public static void initStatement(PreparedStatement statement, ArrayList<Field> notNullFields,
                                     ArrayList<Object> values) throws SQLException {
        for (int i = 0; i < notNullFields.size(); i++) {
            Field currField = notNullFields.get(i);
            Object currValue = values.get(i);

            if (currField.getType() == Integer.class) {
                statement.setInt(i + 1, (Integer) currValue);
            }
            if (currField.getType() == String.class) {
                statement.setString(i + 1, currValue + "%");
            }
            if (currField.getType() == java.util.Date.class) {
                java.util.Date date = (java.util.Date) currValue;
                java.sql.Date d = LogicUtils.convertJavaDateToSqlDate(date);
                statement.setDate(i + 1, d);
            }
        }
    }

    public static Row getRow(Integer id) throws SQLException {
        GenericDAO<Contact> contactDAO = new MysqlContactDAO();
        GenericDAO<Phone> phoneDAO = new MysqlPhoneDAO();
        GenericDAO<AttachedFile> fileDAO = new MysqlFileDAO();

        Contact foundContact = contactDAO.find(new Contact(id),null).get(0);
        ArrayList<Phone> phones = phoneDAO.find(new Phone(id),null);
        ArrayList<AttachedFile> files = fileDAO.find(new AttachedFile(id),null);

        return new Row(foundContact, phones, files);
    }

    public static ArrayList<Phone> getPhonesByContact(Contact contact) throws SQLException {
        GenericDAO<Phone> phoneDAO = new MysqlPhoneDAO();
        return phoneDAO.find(new Phone(contact.getId()), null);
    }

    public static Integer[] getIdsFromContactList(ArrayList<Contact> contacts){
        Integer[] ids = new Integer[contacts.size()];
        for(int i=0; i<contacts.size(); i++){
            ids[i] = contacts.get(i).getId();
        }
        return ids;
    }

    public static ArrayList<AttachedFile> getFilesByContact(Contact contact) throws SQLException {
        GenericDAO<AttachedFile> fileDAO = new MysqlFileDAO();
        return fileDAO.find(new AttachedFile(contact.getId()), null);
    }

    public static void setDateRange(HttpServletRequest req, Contact c) {
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

        if (Integer.parseInt(monthTo) < 10) monthTo = "0" + monthTo;
        c.setDateFrom(new Date(Integer.parseInt(yearFrom) - 1900, Integer.parseInt(monthFrom) - 1,
                Integer.parseInt(dateFrom)));
        c.setDateTo(new Date(Integer.parseInt(yearTo) - 1900, Integer.parseInt(monthTo),
                Integer.parseInt(dateTo)));
    }

    private static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Integer getContactsNumber(Contact criteria) throws SQLException {
        MysqlContactDAO contactDAO = new MysqlContactDAO();
        return contactDAO.getContactsNumber(criteria);
    }

    public static boolean hasCriteria(HttpServletRequest req) {
        Integer deleteFilter = 0;
        if (req.getAttribute("filterDelete") != null)
            deleteFilter = Integer.parseInt((String) req.getAttribute("filterDelete"));

        if (deleteFilter == 1) {
            req.getSession().removeAttribute("criteria");
        }
        return req.getSession().getAttribute("criteria") != null;
    }

    public static Integer[] getIdsForDelete(HttpServletRequest req){
        Integer[] deletedContactsId;
        Object toDeleteAttribute = req.getAttribute("toDelete");
        if(toDeleteAttribute != null) {
            if (toDeleteAttribute instanceof String[]) {
                deletedContactsId = LogicUtils.parseStringToInt((String[]) toDeleteAttribute);
            } else {
                deletedContactsId = new Integer[1];
                deletedContactsId[0] = Integer.parseInt((String) toDeleteAttribute);
            }
        } else {
            deletedContactsId = new Integer[]{};
        }
        return deletedContactsId;
    }

    public static Integer[] parseIdsFromList(ArrayList<String> list){
        Integer[] arr = new Integer[list.size()];
        for(int i=0; i<list.size(); i++){
            arr[i] = Integer.parseInt(list.get(i));
        }
        return arr;
    }

}
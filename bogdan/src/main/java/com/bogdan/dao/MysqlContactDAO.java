package com.bogdan.dao;

import com.bogdan.logic.LogicUtils;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class MysqlContactDAO implements GenericDAO<Contact> {

    private static final Logger LOGGER = Logger.getLogger("contactDAO_logger");

    public MysqlContactDAO(){
    }

    public int insert(Contact c) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO contact_book (first_name,last_name,patronymic,gender,marital_status,"
                    + "website_url,email,job_place,postal_code,date_of_birth,state,city,street,house_number,"
                    + "photo_url,comment) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getFirst_name());
            ps.setString(2, c.getLast_name());
            ps.setString(3, c.getPatronymic());
            ps.setString(4, c.getGender());
            ps.setString(5, c.getMarital_status());
            ps.setString(6, c.getWebsite_url());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getJob_place());
            ps.setString(9, c.getPostal_code());
            ps.setDate(10, new java.sql.Date(c.getBirthDate().getTime()));
            ps.setString(11, c.getState());
            ps.setString(12, c.getCity());
            ps.setString(13, c.getStreet());
            ps.setString(14, c.getHouse_number());
            String path;
            if(c.getPhoto() != null){
                path = c.getPhoto().getRelativePath();
            }
            else {
                path = AttachedFile.DEFAULTPHOTOURL;
            }
            ps.setString(15, path);
            ps.setString(16, c.getComment());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else return -1;
        } finally{
            if(ps != null){
                ps.close();
            }
            if(conn != null) {
                conn.close();
            }
        }
    }

    public boolean delete(int key) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try{
            String sql = "UPDATE contact_book SET deleted = 1 WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, key);
            ps.execute();
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        finally{
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }

    public ArrayList<Contact> find(Contact data) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        StringBuilder sql = new StringBuilder("SELECT * FROM contact_book WHERE deleted=0 AND");
        Statement statement = conn.createStatement();
        ArrayList<Contact> list = null;
        ArrayList<Field> notNullFields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        try {
            Field[] fields =  Contact.class.getDeclaredFields();
            LogicUtils.initLists(fields, notNullFields, values, data);
            String forAppend;
            String fieldName;
            Object fieldValue;
            for(int i=0; i<notNullFields.size(); i++) {
                fieldName = notNullFields.get(i).getName();
                fieldValue = values.get(i);
                if (notNullFields.get(i).getType() != int.class && notNullFields.get(i).getType() != Integer.class) {
                    if (notNullFields.get(i).getType() == AttachedFile.class) {
                        fieldName = "photo_url";
                        AttachedFile photo = (AttachedFile) fieldValue;
                        fieldValue = photo.getRelativePath();
                    }
                    forAppend = " " + fieldName + " LIKE '" + fieldValue + "%'";
                } else {
                    forAppend = String.format(" %s = %d", fieldName, (int) fieldValue);
                }
                if (i < notNullFields.size() - 1) {
                    forAppend += " AND";
                } else forAppend += ";";
                sql.append(forAppend);
            }

            LOGGER.info(sql.toString());
            ResultSet set = statement.executeQuery(sql.toString());
            list =  LogicUtils.getContactFromResultSet(set);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }

        if(list.size() != 0) return list;
        else return null;
    }

    public ArrayList<Contact> getAll() throws SQLException{
        Connection conn = MysqlDAOFactory.createConnection();
        Statement statement = null;
        ArrayList<Contact> list = null;
        try{
            String sql = "SELECT * FROM contact_book WHERE deleted = 0";
            statement = conn.createStatement();
            ResultSet lines = statement.executeQuery(sql);

            list = LogicUtils.getContactFromResultSet(lines);

            if(list.size() > 0){
                return list;
            }
            else return null;

        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }
    }

    public boolean update(int key, Contact c) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try{
            String sql = "UPDATE contact_book SET first_name=?,last_name=?,patronymic=?,gender=?,"
                    +"marital_status=?,website_url=?,email=?,job_place=?,postal_code=?,date_of_birth=?,state=?,"
                    +"city=?,street=?,house_number=?,photo_url=?,comment=? WHERE id = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, c.getFirst_name());
            ps.setString(2, c.getLast_name());
            ps.setString(3, c.getPatronymic());
            ps.setString(4, c.getGender());
            ps.setString(5, c.getMarital_status());
            ps.setString(6, c.getWebsite_url());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getJob_place());
            ps.setString(9, c.getPostal_code());
            ps.setDate(10, new java.sql.Date(c.getBirthDate().getTime()));
            ps.setString(11, c.getState());
            ps.setString(12, c.getCity());
            ps.setString(13, c.getStreet());
            ps.setString(14, c.getHouse_number());
            String path;
            if(c.getPhoto() != null){
                path = c.getPhoto().getRelativePath();
            }
            else {
                path = AttachedFile.DEFAULTPHOTOURL;
            }
            ps.setString(15, path);
            ps.setString(16, c.getComment());
            ps.setInt(17, key);
            LOGGER.info(sql);
            ps.execute();

            return true;
        }
        finally{
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }
}

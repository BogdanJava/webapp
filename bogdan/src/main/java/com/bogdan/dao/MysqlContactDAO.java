package com.bogdan.dao;

import com.bogdan.logic.LogicUtils;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Limit;
import com.bogdan.pojo.Row;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MysqlContactDAO implements GenericDAO<Contact> {

    private static final Logger LOGGER = Logger.getLogger("contactDAO_logger");

    public MysqlContactDAO(){
    }

    public ArrayList<Contact> getBirthdays(Contact contact) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        String sql = "SELECT * FROM contact_book WHERE deleted=0 AND date_format(date_of_birth, \'%d%m\') = ?";
        String toInsert = null;
        PreparedStatement statement = null;
        ArrayList<Contact> contacts = null;
        try {
            statement = conn.prepareStatement(sql);
            toInsert = String.format("%d%d", contact.getBirthDate().getDate(), contact.getBirthDate().getMonth() + 1);
            LOGGER.info(toInsert);
            statement.setString(1, toInsert);
            contacts = LogicUtils.getContactFromResultSet(statement.executeQuery());
        } finally {
            if(conn != null) conn.close();
            if(statement != null) statement.close();
        }
        return contacts;
    }

    public Integer getContactsNumber(Contact data) throws SQLException {
        if(data == null){
            data = new Contact();
        }
        Connection conn = MysqlDAOFactory.createConnection();
        String sql = "SELECT COUNT(*) total FROM contact_book WHERE deleted=0";
        PreparedStatement statement = null;
        Integer count = 0;
        ArrayList<Field> notNullFields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        try {
            Field[] fields =  Contact.class.getDeclaredFields();
            LogicUtils.initLists(fields, notNullFields, values, data);
            String dateRange = " AND date_of_birth >=? AND date_of_birth <=?";
            String query = LogicUtils.getQuery(sql, notNullFields, values);
            if(data.getDateFrom() != null)
                query += dateRange;

            LOGGER.info(query);
            statement = conn.prepareStatement(query);
            LogicUtils.initStatement(statement, notNullFields, values);
            ResultSet set = statement.executeQuery();
            if(set.next())
            count = set.getInt("total");
        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }
        return count;
    }

    @Override
    public ArrayList<Contact> getLimited(int from, int number, int contactId) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try{
            String sql = "SELECT * FROM contact_book WHERE deleted=0 LIMIT ?, ?";
            ps = conn.prepareStatement(sql);
            LOGGER.info(String.format("%s, %s,%s", sql, String.valueOf(from), String.valueOf(number)));
            ps.setInt(1, from);
            ps.setInt(2, number);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Contact> contacts = LogicUtils.getContactFromResultSet(resultSet);
            return contacts;
        } finally {
            if(conn != null) conn.close();
            if(ps != null) ps.close();
        }
    }

    public ArrayList<Contact> getLimitedContacts(int from, int number) throws SQLException {
        return getLimited(from, number, 0);
    }

    public int insert(Contact c) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO contact_book (first_name,last_name,patronymic,gender,marital_status,"
                    + "website_url,email,job_place,postal_code,date_of_birth,state,city,street,house_number,"
                    + "photo_url,comment) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPatronymic());
            ps.setString(4, c.getGender());
            ps.setString(5, c.getMaritalStatus());
            ps.setString(6, c.getWebsiteUrl());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getJobPlace());
            ps.setString(9, c.getPostalCode());
            ps.setDate(10, new java.sql.Date(c.getBirthDate().getTime()));
            ps.setString(11, c.getState());
            ps.setString(12, c.getCity());
            ps.setString(13, c.getStreet());
            ps.setString(14, c.getHouseNumber());
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

    public ArrayList<Contact> find(Contact data, Limit limit) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        String sql = "SELECT * FROM contact_book WHERE deleted=0";
        PreparedStatement statement = null;
        ArrayList<Contact> list = null;
        ArrayList<Field> notNullFields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        try {
            Field[] fields =  Contact.class.getDeclaredFields();
            LogicUtils.initLists(fields, notNullFields, values, data);
            String dateRange = " AND date_of_birth >=? AND date_of_birth <=?";
            String query = LogicUtils.getQuery(sql, notNullFields, values);
            if(data.getDateFrom() != null)
            query += dateRange;

            if(limit != null) query += limit.toString();
            LOGGER.info(query);
            statement = conn.prepareStatement(query);
            LogicUtils.initStatement(statement, notNullFields, values);
            ResultSet set = statement.executeQuery();
            list =  LogicUtils.getContactFromResultSet(set);
        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }
        return list;
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

            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPatronymic());
            ps.setString(4, c.getGender());
            ps.setString(5, c.getMaritalStatus());
            ps.setString(6, c.getWebsiteUrl());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getJobPlace());
            ps.setString(9, c.getPostalCode());
            ps.setDate(10, new java.sql.Date(c.getBirthDate().getTime()));
            ps.setString(11, c.getState());
            ps.setString(12, c.getCity());
            ps.setString(13, c.getStreet());
            ps.setString(14, c.getHouseNumber());
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
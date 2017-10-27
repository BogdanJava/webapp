package com.bogdan.dao;

import com.bogdan.logic.LogicUtils;
import com.bogdan.pojo.Phone;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class MysqlPhoneDAO implements GenericDAO<Phone> {

    private static final Logger LOGGER = Logger.getLogger("phone_logger");

    public int insert(Phone p) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO phone_book (state_code,operator_code,number,phone_type,comment,"
                    +"contact_id) VALUES (?,?,?,?,?,?); ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getStateCode());
            ps.setString(2, p.getOperatorCode());
            ps.setString(3, p.getNumber());
            ps.setString(4, p.getType());
            ps.setString(5, p.getComment());
            ps.setInt(6, p.getContactId());
            ps.execute();
            return 1;
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
        try {
            String sql = "UPDATE phone_book SET deleted = 1 WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, key);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public ArrayList<Phone> getLimited(int from, int number, int contactId) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try{
            String sql = "SELECT * FROM phone_book WHERE contact_id = ? LIMIT ?, ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, contactId);
            ps.setInt(2, from);
            ps.setInt(3, number);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Phone> phones = LogicUtils.getPhonesFromResultSet(resultSet);
            return phones;
        } finally {
            if(conn != null) conn.close();
            if(ps != null) ps.close();
        }
    }

    public ArrayList<Phone> find(Phone data) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        String sql = "SELECT * FROM phone_book WHERE deleted=0";
        PreparedStatement statement = null;
        ArrayList<Phone> list = null;
        ArrayList<Field> notNullFields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        try {
            Field[] fields =  Phone.class.getDeclaredFields();
            LogicUtils.initLists(fields, notNullFields, values, data);
            String sqlString = LogicUtils.getQuery(sql, notNullFields, values);
            statement = conn.prepareStatement(sqlString);
            LogicUtils.initStatement(statement, notNullFields, values);
            LOGGER.info(sqlString);
            ResultSet set = statement.executeQuery();
            list =  LogicUtils.getPhonesFromResultSet(set);
        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }

        return list;
    }

    public ArrayList<Phone> getAll(int contactId) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement statement = null;
        ArrayList<Phone> list = new ArrayList<>();
        try{
            String sql = "SELECT * FROM phone_book pb WHERE deleted = 0 AND contact_id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, contactId);
            ResultSet lines = statement.executeQuery();

            return LogicUtils.getPhonesFromResultSet(lines);

        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }
    }

    public boolean update(int key, Phone p) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE phone_book SET state_code=?,operator_code=?,number=?,phone_type=?,comment=?"
                    + " WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getStateCode());
            ps.setString(2, p.getOperatorCode());
            ps.setString(3, p.getNumber());
            ps.setString(4, p.getType());
            ps.setString(5, p.getComment());
            ps.setInt(6, key);

            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
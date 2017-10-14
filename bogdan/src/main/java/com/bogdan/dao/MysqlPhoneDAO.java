package com.bogdan.dao;

import com.bogdan.pojo.Phone;

import java.sql.*;
import java.util.ArrayList;

public class MysqlPhoneDAO implements GenericDAO<Phone> {

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

    public ArrayList<Phone> find(Phone data) {
        return null;
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

            while(lines.next()){
                Phone p = new Phone();
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
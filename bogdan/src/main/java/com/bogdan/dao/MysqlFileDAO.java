package com.bogdan.dao;

import com.bogdan.logic.LogicUtils;
import com.bogdan.pojo.AttachedFile;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class MysqlFileDAO implements GenericDAO<AttachedFile> {

    public int insert(AttachedFile file) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO attached_files (relative_path,description,contact_id,real_path, name, file_type)"
                    +" VALUES (?,?,?,?,?,?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, file.getRelativePath());
            ps.setString(2, file.getDescription());
            ps.setInt(3, file.getContactId());
            ps.setString(4, file.getRealPath());
            ps.setString(5, file.getName());
            ps.setString(6, file.getType());

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
        try{
            String sql = "UPDATE attached_files SET deleted = 1 WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, key);
            ps.executeUpdate();
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

    public ArrayList<AttachedFile> find(AttachedFile data) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        String sql = "SELECT * FROM attached_files WHERE deleted=0 AND";
        Statement statement = conn.createStatement();
        ArrayList<AttachedFile> list = null;
        ArrayList<Field> notNullFields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        try {
            Field[] fields =  AttachedFile.class.getDeclaredFields();
            LogicUtils.initLists(fields, notNullFields, values, data);

            String sqlString = LogicUtils.getQuery(sql, notNullFields, values);

            ResultSet set = statement.executeQuery(sqlString);
            list =  LogicUtils.getFilesFromResultSet(set);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }

        if(list.size() != 0) return list;
        else return null;

    }

    public ArrayList<AttachedFile> getAll(int contactId) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement statement = null;
        ArrayList<AttachedFile> list;
        try{
            String sql = "SELECT * FROM attached_files af WHERE af.deleted = 0 AND af.contact_id = (?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, contactId);
            ResultSet lines = statement.executeQuery();

            return LogicUtils.getFilesFromResultSet(lines);

        } finally {
            if(statement != null) statement.close();
            if(conn != null) conn.close();
        }
    }

    public boolean update(int key, AttachedFile file) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE attached_files SET relative_path=?,description=?,real_path=?,"
                    + "file_type=? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, file.getRelativePath());
            ps.setString(2, file.getDescription());
            ps.setString(3, file.getRealPath());
            ps.setString(4, file.getType());
            ps.setInt(5, key);

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

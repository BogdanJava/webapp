package com.bogdan.dao;

import com.bogdan.pojo.AttachedFile;

import java.sql.*;
import java.util.ArrayList;

public class MysqlFileDAO implements GenericDAO<AttachedFile> {

    public int insert(AttachedFile file) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO attached_files (relative_path,description,contact_id,real_path, name)"
                    +" VALUES (?,?,?,?,?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, file.getRelativePath());
            ps.setString(2, file.getDescription());
            ps.setInt(3, file.getContactId());
            ps.setString(4, file.getRealPath());
            ps.setString(5, file.getName());

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

    public ArrayList<AttachedFile> find(AttachedFile data) {
        return null;
    }

    public ArrayList<AttachedFile> getAll(int contactId) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement statement = null;
        ArrayList<AttachedFile> list = new ArrayList<>();
        try{
            String sql = "SELECT * FROM attached_files af WHERE af.deleted = 0 AND af.contact_id = (?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, contactId);
            ResultSet lines = statement.executeQuery();

            while(lines.next()){
                AttachedFile file = new AttachedFile();
                file.setRelativePath(lines.getString("relative_path"));
                file.setRealPath(lines.getString("real_path"));
                file.setName(lines.getString("name"));
                file.setContactId(lines.getInt("contact_id"));
                list.add(file);
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

    public boolean update(int key, AttachedFile file) throws SQLException {
        Connection conn = MysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE attached_files SET relative_path=?,description=?,real_path=?"
                    + " WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, file.getRelativePath());
            ps.setString(2, file.getDescription());
            ps.setString(3, file.getRealPath());
            ps.setInt(4, key);

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

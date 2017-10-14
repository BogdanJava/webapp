package com.bogdan.dao;

import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.sun.org.apache.regexp.internal.RESyntaxException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class MysqlContactDAO implements GenericDAO<Contact> {

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
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPatronymic());
            ps.setString(4, c.getGender());
            ps.setString(5, c.getMaritalStatus());
            ps.setString(6, c.getWebsiteURL());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getJobPlace());
            ps.setString(9, c.getPostalCode());
            ps.setDate(10, new java.sql.Date(c.getBirthDate().getTime()));
            ps.setString(11, c.getState());
            ps.setString(12, c.getCity());
            ps.setString(13, c.getStreet());
            ps.setString(14, c.getHouseNumber());
            ps.setString(15, c.getPhoto().getRelativePath());
            ps.setString(16, c.getComment());
            ps.execute();

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
        return null;
    }

    public ArrayList<Contact> getAll() throws SQLException{
        Connection conn = MysqlDAOFactory.createConnection();
        Statement statement = null;
        ArrayList<Contact> list = new ArrayList<>();
        try{
            String sql = "SELECT * FROM contact_book WHERE deleted = 0";
            statement = conn.createStatement();
            ResultSet lines = statement.executeQuery(sql);

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
                c.setPhoto(af);
                c.setPostalCode(lines.getString("postal_code"));
                c.setStreet(lines.getString("street"));
                c.setWebsiteURL(lines.getString("website_url"));
                list.add(c);
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
            ps.setString(6, c.getWebsiteURL());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getJobPlace());
            ps.setString(9, c.getPostalCode());
            ps.setDate(10, new java.sql.Date(c.getBirthDate().getTime()));
            ps.setString(11, c.getState());
            ps.setString(12, c.getCity());
            ps.setString(13, c.getStreet());
            ps.setString(14, c.getHouseNumber());
            ps.setString(15, c.getPhoto().getRelativePath());
            ps.setString(16, c.getComment());
            ps.setInt(17, key);
            ps.execute();

            return true;
        }
        finally{
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }
}

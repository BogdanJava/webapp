package com.bogdan.dao;

import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDAOFactory extends DAOFactory{

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://localhost:3306/shishkin_bogdan_db";
    private static InitialContext initContext = null;
    private static DataSource ds = null;

    public static Connection createConnection() throws SQLException {
        try {
            if (initContext == null) {
                initContext = new InitialContext();
            }
            if (ds == null) {
                ds = (DataSource) initContext.lookup("java:comp/env/jdbc/bogdanDB");
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds.getConnection();
    }

    public GenericDAO<Contact> getContactDAO(){
        return new MysqlContactDAO();
    }

    public GenericDAO<Phone> getPhoneDAO(){
        return new MysqlPhoneDAO();
    }

    public GenericDAO<AttachedFile> getFileDAO(){
        return new MysqlFileDAO();
    }
}

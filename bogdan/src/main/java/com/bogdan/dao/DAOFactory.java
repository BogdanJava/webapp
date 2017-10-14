package com.bogdan.dao;

import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Contact;
import com.bogdan.pojo.Phone;

public abstract class DAOFactory {
    public static final int MYSQL = 1;

    public abstract GenericDAO<Contact> getContactDAO();
    public abstract GenericDAO<Phone> getPhoneDAO();
    public abstract GenericDAO<AttachedFile> getFileDAO();

    public static DAOFactory getDAOFactory(int whichFactory){
        switch(whichFactory){
            case MYSQL: return new MysqlDAOFactory();
            default: return null;
        }
    }
}

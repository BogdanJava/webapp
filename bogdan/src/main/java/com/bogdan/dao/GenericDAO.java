package com.bogdan.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericDAO<T> {
    int insert(T data) throws SQLException;
    boolean delete(int key) throws SQLException;
    ArrayList<T> find(T data) throws SQLException;
    boolean update(int key, T data) throws SQLException;
}

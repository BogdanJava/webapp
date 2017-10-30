package com.bogdan.dao;

import com.bogdan.pojo.Limit;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericDAO<T> {

    int insert(T data) throws SQLException;
    boolean delete(int key) throws SQLException;
    ArrayList<T> find(T data, Limit limit) throws SQLException;
    boolean update(int key, T data) throws SQLException;
    ArrayList<T> getLimited(int from, int number, int contactId) throws SQLException;
}

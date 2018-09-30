package com.gleb.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    void add(T t) throws SQLException;

    T get(long id, T t) throws SQLException, InvocationTargetException, IllegalAccessException;

    void update(long id);

    void remove(long id);

    List<T> getAll();
}

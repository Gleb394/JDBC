package com.gleb.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    void add(T t);

    T get(Long id);

    void update(Long id);

    void remove(Long id);

    List<T> getAll();
}

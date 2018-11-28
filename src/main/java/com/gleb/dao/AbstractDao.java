package com.gleb.dao;

import com.gleb.query.QueryDao;
import com.gleb.reflect.ReflectParseClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
    private ReflectParseClass reflectParseClass = new ReflectParseClass();
    private QueryDao queryDao = new QueryDao();
    private Class<?> clazz;

    public final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
        this.clazz = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void add(T t) {
        PreparedStatement statement;
        int size = reflectParseClass.parseAnnotationName(t.getClass()).size();
        try {
            statement = connection.prepareStatement(queryDao.queryAdd(t.getClass()));
            for (int i = 0; i < size; i++) {
                Method method = reflectParseClass.getMethod(clazz, i, "get");
                statement.setObject(i + 1, method.invoke(t));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public T get(Long id) {
        T t = null;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(queryDao.queryGet(clazz));
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            t = (T) clazz.newInstance();
            int size = reflectParseClass.parseAnnotationName(t.getClass()).size();
            while (rs.next()) {
                for (int i = 0; i <= size; i++) {
                    Method method = reflectParseClass.getMethod(t.getClass(), i, "set");
                    method.invoke(t, rs.getObject(i + 1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    public void update(Long id) {
        T t;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(queryDao.queryUpdate(clazz));
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            t = (T) clazz.newInstance();
            int size = reflectParseClass.parseAnnotationName(t.getClass()).size();
            while (rs.next()) {
                for (int i = 0; i <= size; i++) {
                    Method method = reflectParseClass.getMethod(t.getClass(), i, "get");
                    statement.setObject(i + 1, method.invoke(t));
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void remove(Long id) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(queryDao.queryGet(clazz));
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        T t;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(queryDao.queryGetAll(clazz));
            ResultSet rs = statement.executeQuery();
            t = (T) clazz.newInstance();
            int size = reflectParseClass.parseAnnotationName(t.getClass()).size();
            while (rs.next()) {
                for (int i = 0; i <= size; i++) {
                    Method method = reflectParseClass.getMethod(t.getClass(), i, "set");
                    method.invoke(t, rs.getObject(i + 1));
                }
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return list;
    }
}

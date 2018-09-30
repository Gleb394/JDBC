package com.gleb.dao;

import com.gleb.query.QueryDao;
import com.gleb.reflect.ReflectParseClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<T>  implements GenericDao<T> {
    private ReflectParseClass reflectParseClass = new ReflectParseClass();
    private QueryDao queryDao = new QueryDao();
    private Class<?> clazz;

    public final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }


    public void add(T t) throws SQLException {
        PreparedStatement statement;
        clazz = t.getClass();

        statement = connection.prepareStatement(queryDao.queryAdd(t.getClass()));
        for (int i = 0; i < reflectParseClass.takeGetOrSetMethod(t.getClass(), "get").size(); i++) {
            Method method = (Method) reflectParseClass.takeGetOrSetMethod(t.getClass(), "get").get(i);
            statement.setObject(i, method);
        }
    }

    public T get(long id, T  t) throws SQLException, InvocationTargetException, IllegalAccessException {
        clazz = t.getClass();
        PreparedStatement statement;
        statement = connection.prepareStatement(queryDao.queryGet(clazz));
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();

        for (int i = 0; i < reflectParseClass.takeGetOrSetMethod(clazz, "set").size(); i++) {
            Method method = (Method) reflectParseClass.takeGetOrSetMethod(clazz, "set").get(i);
            method.invoke(resultSet.getObject(i));
        }

        return null;
    }

    public void update(long id) {

    }

    public void remove(long id) {

    }

    public List<T> getAll() {
        return null;
    }
}

package com.gleb.query;

import com.gleb.reflect.ReflectParseClass;

public class QueryDao {
    ReflectParseClass reflectParseClass = new ReflectParseClass();

    public String queryAdd(Class c) {
        String insertInto = "INSERT INTO ";
        String tableName = reflectParseClass.ParseTableName(c) + " (";
        String nameColumns = reflectParseClass.ParseColumnName(c, "space") + ") ";
        String values = "VALUES ";
        String markers = "(?";

        for (int i = 1; i < reflectParseClass.getCounter(); i++) {
            String buffer = ", ?";
            markers += buffer;
        }

        String last = ");";
        String query = insertInto + tableName + nameColumns + values + markers + last;

        reflectParseClass.setCounter(0);

        return query;
    }

    public String queryGet(Class c) {
        String select = "SELECT ";
        String id1 = "ID, ";
        String nameColumns = reflectParseClass.ParseColumnName(c, "space") + " ";
        String from = "FROM ";
        String tableName = reflectParseClass.ParseTableName(c) + " ";
        String where = "WHERE ";
        String id = "ID = ?;";

        String query = select + id1 + nameColumns + from + tableName + where + id;

        reflectParseClass.setCounter(0);

        return query;
    }

    public String queryUpdate(Class c) {
        String update = "UPDATE ";
        String tableName = reflectParseClass.ParseTableName(c) + " ";
        String set = "SET ";
        String nameColumns = reflectParseClass.ParseColumnName(c, "markers") + " ";
        String where = "WHERE ";
        String id = "ID = ?;";

        String query = update + tableName + set + nameColumns + where + id;

        reflectParseClass.setCounter(0);

        return query;
    }

    public String queryRemove(Class c) {
        String deleteFrom = "DELETE FROM ";
        String tableName = reflectParseClass.ParseTableName(c) + " ";
        String whereId = "WHERE ID = ?;";

        String query = deleteFrom + tableName + whereId;

        return query;
    }

    public String queryGetAll(Class c) {
        String selectId = "SELECT ID, ";
        String nameColumns = reflectParseClass.ParseColumnName(c, "space") + " ";
        String from = "FROM ";
        String tableName = reflectParseClass.ParseTableName(c) + " ";

        String query = selectId + nameColumns + from + tableName;

        reflectParseClass.setCounter(0);

        return query;
    }
}

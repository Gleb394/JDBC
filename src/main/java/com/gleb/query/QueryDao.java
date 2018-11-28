package com.gleb.query;
import com.gleb.reflect.ReflectParseClass;

import java.util.List;

public class QueryDao {
    private ReflectParseClass reflectParseClass = new ReflectParseClass();

    public String queryAdd(Class c) {
        String insertInto = "INSERT INTO ";
        String tableName = reflectParseClass.parseTableName(c) + " (";
        String nameColumns = fullColumnsSpace(c) + ") ";
        String values = "VALUES ";
        String markers = marker(c);
        String last = ");";

        String query = insertInto + tableName + nameColumns + values + markers + last;

        return query;
    }

    public String queryGet(Class c) {
        String select = "SELECT ";
        String id1 = reflectParseClass.AnnotationNameId(c) + ", ";
        String nameColumns = fullColumnsSpace(c) + " ";
        String from = "FROM ";
        String tableName = reflectParseClass.parseTableName(c) + " ";
        String where = "WHERE ";
        String id = reflectParseClass.AnnotationNameId(c) + " = ?;";

        String query = select + id1 + nameColumns + from + tableName + where + id;

        return query;
    }

    public String queryUpdate(Class c) {
        String update = "UPDATE ";
        String tableName = reflectParseClass.parseTableName(c) + " ";
        String set = "SET ";
        String nameColumns = fullColumnsMarker(c) + " ";
        String where = "WHERE ";
        String id = reflectParseClass.AnnotationNameId(c) + " = ?;";

        String query = update + tableName + set + nameColumns + where + id;

        return query;
    }

    public String queryRemove(Class c) {
        String deleteFrom = "DELETE FROM ";
        String tableName = reflectParseClass.parseTableName(c) + " ";
        String whereId = "WHERE "+ reflectParseClass.AnnotationNameId(c) +  " = ?;";

        String query = deleteFrom + tableName + whereId;

        return query;
    }

    public String queryGetAll(Class c) {
        String selectId = "SELECT " + reflectParseClass.AnnotationNameId(c) + ", ";
        String nameColumns = reflectParseClass.parseTableName(c) + " ";
        String from = "FROM ";
        String tableName = reflectParseClass.parseTableName(c) + " ";

        String query = selectId + nameColumns + from + tableName;

        return query;
    }

    private String fullColumnsMarker(Class c) {
        List annotationName = reflectParseClass.parseAnnotationName(c);
        String buffer;
        String nameColumns = null;
        String space = ", ";
        String markers = " = ?";

        for (int i = 0; i < annotationName.size(); i++) {
            if (i == 0) {
                nameColumns = (String) annotationName.get(i);
            } else {
                buffer = (String) annotationName.get(i);
                nameColumns += space + buffer + markers;
            }
        }
        return nameColumns;
    }

    private String fullColumnsSpace(Class c) {
        List annotationName = reflectParseClass.parseAnnotationName(c);
        String buffer;
        String nameColumns = null;
        String space = ", ";

        for (int i = 0; i < annotationName.size(); i++) {
            if (i == 0) {
                nameColumns = (String) annotationName.get(i);
            } else {
                buffer = (String) annotationName.get(i);
                nameColumns += space + buffer;
            }
        }
        return nameColumns;
    }

    private String marker(Class c) {
        List annotationName = reflectParseClass.parseAnnotationName(c);
        String markers = "(?";

        for (int i = 0; i < annotationName.size() - 1; i++) {
            String buffer = ", ?";
            markers += buffer;
        }
        return markers;
    }
}

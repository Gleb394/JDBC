package com.gleb.reflect;

import com.gleb.annotation.NameColumn;
import com.gleb.annotation.NameTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectParseClass<T> {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String ParseTableName(Class c) {
            Annotation[] annotations = c.getAnnotations();
            for (Annotation annotation : annotations) {
                annotation = c.getAnnotation(NameTable.class);
                NameTable nameTable = (NameTable) annotation;
                return nameTable.name();
            }
        return null;
    }

    public String ParseColumnName(Class c, String marker) {
        String buffer;
        String nameColumns = null;
        String space = ", ";
        String markers = " = ?";

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                annotation = field.getAnnotation(NameColumn.class);
                NameColumn nameColumn = (NameColumn) annotation;
                if (counter == 0) {
                    if (marker.equals("space")) {
                        nameColumns = nameColumn.name();
                        counter++;
                    } else if (marker.equals("markers")) {
                        nameColumns = nameColumn.name() + markers;
                        counter++;
                    }
                } else {
                    if (marker.equals("space")) {
                        buffer = nameColumn.name();
                        nameColumns += space + buffer;
                        counter++;
                    } else if (marker.equals("markers")) {
                        buffer = nameColumn.name();
                        nameColumns += space + buffer + markers;
                        counter++;
                    }
                }
            }
        }
        return nameColumns;
    }

    public List<Method> takeGetOrSetMethod(Class c , String nameMethod) {
       Method [] methods = c.getMethods();
       int counterGet = 0;
       int counterSet = 0;
       List<Method> get = new ArrayList<>();
       List<Method> set = new ArrayList<>();

       for (Method method : methods) {
           if (isGetter(method)) {
               get.add(counterGet, method);
               counterGet++;
           }
           if (isSetter(method)) {
               set.add(counterSet ,method);
               counterSet++;
           }
       }
       if (nameMethod.equals("get")) {
           return get;
       }
       if (nameMethod.equals("set")) {
           return set;
       }
       return null;
    }

    private static boolean isGetter(Method method){
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }

    private static boolean isSetter(Method method){
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }
}

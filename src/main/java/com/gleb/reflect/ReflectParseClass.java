package com.gleb.reflect;

import com.gleb.annotation.NameColumn;
import com.gleb.annotation.NameTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectParseClass {

    public String parseTableName(Class c) {
        Annotation[] annotations = c.getAnnotations();
        for (Annotation annotation : annotations) {
            annotation = c.getAnnotation(NameTable.class);
            NameTable nameTable = (NameTable) annotation;
            return nameTable.name();
        }
        return null;
    }

    public List<String> parseAnnotationName(Class c) {
        List<String> nameAnnotation = new ArrayList<>();

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                annotation = field.getAnnotation(NameColumn.class);
                NameColumn nameColumn = (NameColumn) annotation;
                if (!nameColumn.name().startsWith("ID") || !nameColumn.name().endsWith("ID"))
                    nameAnnotation.add(nameColumn.name());
            }
        }
        return nameAnnotation;
    }

    public String AnnotationNameId(Class c) {
        String id = null;

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                annotation = field.getAnnotation(NameColumn.class);
                NameColumn nameColumn = (NameColumn) annotation;
                if (nameColumn.name().startsWith("ID") || nameColumn.name().endsWith("ID"))
                    id = nameColumn.name();
                    return id;
            }
        }
        return null;
    }

    public Method getMethod(Class c, int counter, String nameMethod) {
        Field[] fields = c.getDeclaredFields();
        return takeGetOrSetMethod(c, fields[counter], nameMethod);
    }

    private Method takeGetOrSetMethod(Class c , Field field, String nameMethod) {
       Method [] methods = c.getMethods();

       for (Method method : methods) {
           if (method.getName().toUpperCase().endsWith(field.getName().toUpperCase())) {
               if (isGetter(method, nameMethod)) {
                   return method;
               }
               if (isSetter(method, nameMethod)) {
                   return method;
               }
           }
       }
       return null;
    }

    private static boolean isGetter(Method method, String nameMethod){
        if (!method.getName().startsWith(nameMethod)) {
            return false;
        }
        if (method.getName().endsWith("Id")) {
            return false;
        }
        if (method.getName().endsWith("Class")) {
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

    private static boolean isSetter(Method method, String nameMethod){
        if (!method.getName().startsWith(nameMethod)) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }
}

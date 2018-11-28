package com.gleb.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassData {


    public static Class<?> getClass(String className) {
        Class<?> someClass = null;
        try {
            someClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return someClass;
    }

    public static Type getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }
        return ((ParameterizedType) superclassType).getActualTypeArguments()[0];
    }
}

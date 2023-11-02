package org.gongxuanzhang.easybyte.core.tool;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * support get class info
 *
 * @author gongxuanzhang gongxuanzhangmelt@gmail.com
 **/
public class TypeUtils {

    private TypeUtils() {

    }

    /**
     * return first generic class if target have.
     *
     * @param clazz target
     * @return maybe null
     **/
    public static Class<?> getFirstGenericType(Class<?> clazz) {
        Type[] typeArguments = getGenericTypes(clazz);
        if (typeArguments.length == 0) {
            return null;
        }
        Type firstGeneric = typeArguments[0];
        if (firstGeneric instanceof Class) {
            return (Class<?>) firstGeneric;
        }
        return null;
    }


    private static Type[] getGenericTypes(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return new Type[0];
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getActualTypeArguments();
    }


}



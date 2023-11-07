package org.gongxuanzhang.easybyte.core.tool;

import org.gongxuanzhang.easybyte.core.ReadConverter;

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
     * if target class is subclass from interface and the interface have generic.find it.
     *
     * @param origin         target class
     * @param interfaceClass target interface not a class
     * @return generic array or null
     **/
    public static Type[] getTargetInterfaceGeneric(Class<?> origin, Class<?> interfaceClass) {
        Type[] genericInterfaces = origin.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) genericInterface).getRawType();
                if (rawType == interfaceClass) {
                    return ((ParameterizedType) genericInterface).getActualTypeArguments();
                }
            }
        }
        return new Type[0];
    }

}



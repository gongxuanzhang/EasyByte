package org.gongxuanzhang.easybyte.core.exception;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class GenericNotFoundException extends RuntimeException {


    public GenericNotFoundException(Class<?> type) {
        super(type.getSimpleName() + "generic does not exists");
    }
}

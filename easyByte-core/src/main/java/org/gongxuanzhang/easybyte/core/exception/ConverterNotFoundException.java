package org.gongxuanzhang.easybyte.core.exception;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class ConverterNotFoundException extends RuntimeException {


    public ConverterNotFoundException(Class<?> type) {
        super(type.getSimpleName() + " convert does not exists");
    }
}

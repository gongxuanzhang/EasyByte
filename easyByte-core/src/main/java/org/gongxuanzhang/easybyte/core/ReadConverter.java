package org.gongxuanzhang.easybyte.core;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
@FunctionalInterface
public interface ReadConverter<V> {

    /**
     * convert object from byte array
     *
     * @param bytes from buffer
     * @return maybe null
     **/
    V toObject(byte[] bytes);


}

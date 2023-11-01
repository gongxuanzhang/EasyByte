package org.gongxuanzhang.easybyte.core;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
@FunctionalInterface
public interface ReadConvert<V> {

    /**
     * convert object from byte array
     *
     * @param bytes from buffer
     * @return maybe null
     **/
    V toBytes(byte[] bytes);


}

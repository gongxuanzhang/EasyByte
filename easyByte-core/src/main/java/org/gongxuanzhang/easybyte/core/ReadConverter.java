package org.gongxuanzhang.easybyte.core;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
@FunctionalInterface
public interface ReadConverter<V> {

    /**
     * convert object from byte array
     *
     * @param bytes  from buffer,if length less zero bytes is null.
     * @param length general length is bytes length but sometime the length have special meaning
     * @return maybe null
     **/
    V toObject(byte[] bytes, int length);


}

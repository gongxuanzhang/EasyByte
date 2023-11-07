package org.gongxuanzhang.easybyte.core;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
@FunctionalInterface
public interface WriteConverter<V> {

    /**
     * convert sth to byte array
     *
     * @param v can convert value
     * @return byte array  ,may be length is zero. if the length less zero has special meaning return null
     **/
    byte[] toBytes(V v);


}

package org.gongxuanzhang.easybyte.core;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
@FunctionalInterface
public interface ByteConvert<V> {

    /**
     * convert sth to byte array
     *
     * @param v can convert value
     * @return byte array not null ,may be length is zero
     **/
    byte[] toBytes(V v);


}

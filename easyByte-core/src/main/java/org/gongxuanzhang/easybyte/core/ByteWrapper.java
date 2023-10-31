package org.gongxuanzhang.easybyte.core;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
@FunctionalInterface
public interface ByteWrapper {

    /**
     * serialize my self
     *
     * @return byte array not null,may be length is zero
     **/
    byte[] toBytes();
}

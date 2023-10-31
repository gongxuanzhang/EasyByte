package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.exception.WrapperNotFoundException;

import java.util.Collection;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public interface DynamicByteBuffer {


    /**
     * put a byte
     *
     * @param b a byte
     * @return this
     **/
    DynamicByteBuffer put(byte b);

    /**
     * put a short
     *
     * @param s a short
     * @return this
     **/
    DynamicByteBuffer putShort(short s);

    /**
     * put a int
     *
     * @param i a int
     * @return this
     **/
    DynamicByteBuffer putInt(int i);


    /**
     * put a long
     *
     * @param l a long
     * @return this
     **/
    DynamicByteBuffer putLong(long l);

    /**
     * put a float
     *
     * @param f a float
     * @return this
     **/
    DynamicByteBuffer putFloat(float f);

    /**
     * put a double
     *
     * @param d a double
     * @return this
     **/
    DynamicByteBuffer putDouble(double d);

    /**
     * put a char
     *
     * @param c a char
     * @return this
     **/
    DynamicByteBuffer putChar(char c);

    /**
     * put a boolean
     *
     * @param bool a boolean
     * @return this
     **/
    DynamicByteBuffer putBoolean(boolean bool);


    /**
     * put a collection
     * the order of puts depends on the order of traversal
     * item in collection will converted to byte array for put buffer
     * primitive type and packing type will invoke corresponding method
     * reference type will find convert or wrapper to invoke method
     * if neither convert nor wrapper can be found,an {@link WrapperNotFoundException} will be throw
     *
     * @param collection use for put
     * @return this
     **/
    DynamicByteBuffer putCollection(Collection<?> collection);

    /**
     * specify a converter to convert item
     * {@link this#putCollection(Collection)}
     *
     * @param collection a collection
     * @param convert    specify convert
     * @return this
     **/
    <V> DynamicByteBuffer putCollection(Collection<V> collection, ByteConvert<V> convert);
}

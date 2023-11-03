package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;

/**
 * can append and get string.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface StringByteBuffer extends ConvertRegister {

    /**
     * special convert to convert string.
     *
     * @param convert String convert
     * @return string
     **/
    String getString(ReadConverter<String> convert);

    /**
     * get a string from buffer.
     * find convert by clazz
     *
     * @return string
     **/
    String getString();

    /**
     * append a string to buffer
     * special convert
     *
     * @param s       string
     * @param convert special convert
     * @return this
     **/
    StringByteBuffer appendString(String s, WriteConverter<String> convert);

    /**
     * put a object in buffer
     * find convert by
     *
     * @param s string
     * @return this
     **/
    StringByteBuffer appendString(String s);

}

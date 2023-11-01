package org.gongxuanzhang.easybyte.core;

/**
 * can put and get object.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface ReferenceByteBuffer {

    /**
     * get a object from buffer.
     * find convert by clazz
     *
     * @param clazz object type
     * @return map
     **/
    <K> K getObject(Class<K> clazz);

    /**
     * special convert to convert object.
     *
     * @param convert object convert
     * @return object
     **/
    <K> K getObject(ReadConvert<K> convert);

    /**
     * put a object in buffer
     * find convert by
     *
     * @param o object
     * @return this
     **/
    ReferenceByteBuffer putObject(Object o);

    /**
     * put a object in buffer
     * special convert
     *
     * @param o       object
     * @param convert special convert
     * @return this
     **/
    <K> ReferenceByteBuffer putObject(K o, WriteConvert<K> convert);

}

package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;

/**
 * can put and get object.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface ReferenceByteBuffer extends ConvertRegister {

    /**
     * special convert to convert object.
     *
     * @param convert object convert
     * @return object
     **/
    <K> K getObject(ReadConverter<K> convert);

    /**
     * get a object from buffer.
     * find convert by clazz
     *
     * @param clazz object type
     * @return map
     **/
    default <K> K getObject(Class<K> clazz) {
        return getObject(findReadConverter(clazz));
    }

    /**
     * put a object in buffer
     * special convert
     *
     * @param o       object
     * @param convert special convert
     * @return this
     **/
    <K> ReferenceByteBuffer putObject(K o, WriteConverter<K> convert);

    /**
     * put a object in buffer
     * find convert by
     *
     * @param o object
     * @return this
     **/
    ReferenceByteBuffer putObject(Object o);

}

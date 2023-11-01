package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConvert;
import org.gongxuanzhang.easybyte.core.WriteConvert;
import org.gongxuanzhang.easybyte.core.exception.ConvertDuplicationException;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface ConvertRegister {


    /**
     * register a writeConvert in register.
     * writeConvert type duplicate result in throw {@link ConvertDuplicationException}
     * The types of subclasses and superclasses can coexist
     * Follow the principle of the proximity
     *
     * @param writeConvert a writeConvert
     **/
    void registerWriteConvert(WriteConvert<?> writeConvert);

    /**
     * register a readConvert in register.
     * readConvert type duplicate result in throw {@link ConvertDuplicationException}
     *
     * @param readConvert a writeConvert
     **/
    void registerWriteConvert(ReadConvert<?> readConvert);


    /**
     * find writeConvert by register . It is may be null
     *
     * @param clazz class
     * @return convert or null
     **/
    <V> WriteConvert<V> findWriteConvert(Class<V> clazz);

    /**
     * find ReadConvert by register . It is may be null.
     *
     * @param clazz class
     * @return convert or null
     **/
    <V> ReadConvert<V> findReadConvert(Class<V> clazz);


}

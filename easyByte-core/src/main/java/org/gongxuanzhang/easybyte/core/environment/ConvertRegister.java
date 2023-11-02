package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.exception.ConvertDuplicationException;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface ConvertRegister {


    /**
     * register a writeConverter in register.
     * writeConverter type duplicate result in throw {@link ConvertDuplicationException}
     * The types of subclasses and superclasses can coexist
     * Follow the principle of the proximity
     *
     * @param writeConverter a writeConverter
     **/
    void registerReadConverter(WriteConverter<?> writeConverter);

    /**
     * register a readConverter in register.
     * readConverter type duplicate result in throw {@link ConvertDuplicationException}
     *
     * @param readConverter a writeConvert
     **/
    void registerReadConverter(ReadConverter<?> readConverter);


    /**
     * find writeConvert by register . It is may be null
     *
     * @param clazz class
     * @return convert or null
     **/
    <V> WriteConverter<V> findWriteConverter(Class<V> clazz);

    /**
     * find ReadConverter by register . It is may be null.
     *
     * @param clazz class
     * @return convert or null
     **/
    <V> ReadConverter<V> findReadConverter(Class<V> clazz);


}

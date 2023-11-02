package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public abstract class BaseConvertRegister implements ConvertRegister {


    @Override
    public void registerReadConverter(WriteConverter<?> writeConverter) {

    }

    @Override
    public void registerReadConverter(ReadConverter<?> readConverter) {

    }

    @Override
    public <V> WriteConverter<V> findWriteConverter(Class<V> clazz) {
        return null;
    }

    @Override
    public <V> ReadConverter<V> findReadConverter(Class<V> clazz) {
        return null;
    }
}

package org.gongxuanzhang.easybyte.core;

/**
 *
 *
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public abstract class BaseConvertRegister implements ConvertRegister {


    @Override
    public void registerWriteConvert(WriteConvert<?> writeConvert) {

    }

    @Override
    public void registerWriteConvert(ReadConvert<?> readConvert) {

    }

    @Override
    public <V> WriteConvert<V> findWriteConvert(Class<V> clazz) {
        return null;
    }

    @Override
    public <V> ReadConvert<V> findReadConvert(Class<V> clazz) {
        return null;
    }
}

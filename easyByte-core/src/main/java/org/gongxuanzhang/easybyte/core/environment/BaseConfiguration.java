package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConvert;
import org.gongxuanzhang.easybyte.core.WriteConvert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public abstract class BaseConfiguration implements EasyByteConfiguration {

    private final Map<Class<?>, WriteConvert<?>> writeConvertMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, ReadConvert<?>> readConvertMap = new ConcurrentHashMap<>();


    @Override
    public void registerWriteConvert(WriteConvert<?> writeConvert) {

        this.writeConvertMap.put(writeConvert.getClass(), writeConvert);
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

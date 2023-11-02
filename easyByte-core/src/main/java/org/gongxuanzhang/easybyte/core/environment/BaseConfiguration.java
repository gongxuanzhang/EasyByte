package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public abstract class BaseConfiguration implements EasyByteConfiguration {

    protected ConvertRegister convertRegister = new MapConvertRegister();

    protected Map<String, String> propertiesMap = new ConcurrentHashMap<>();

    @Override
    public void setProperty(String key, String value) {
        propertiesMap.put(key, value);
    }

    @Override
    public String getProperty(String key) {
        return propertiesMap.get(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return propertiesMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void registerReadConverter(WriteConverter<?> writeConverter) {
        convertRegister.registerReadConverter(writeConverter);
    }

    @Override
    public void registerReadConverter(ReadConverter<?> readConverter) {
        convertRegister.registerReadConverter(readConverter);
    }

    @Override
    public <V> WriteConverter<V> findWriteConverter(Class<V> clazz) {
        return convertRegister.findWriteConverter(clazz);
    }

    @Override
    public <V> ReadConverter<V> findReadConverter(Class<V> clazz) {
        return convertRegister.findReadConverter(clazz);
    }
}

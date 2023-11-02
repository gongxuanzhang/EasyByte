package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.exception.ConvertDuplicationException;
import org.gongxuanzhang.easybyte.core.exception.GenericNotFoundException;
import org.gongxuanzhang.easybyte.core.tool.TypeUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MapConvertRegister implements ConvertRegister {

    private final Map<Class<?>, WriteConverter<?>> writeConvertMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, ReadConverter<?>> readConvertMap = new ConcurrentHashMap<>();


    @Override
    public void registerReadConverter(WriteConverter<?> writeConverter) {
        Class<?> convertClass = writeConverter.getClass();
        Class<?> firstGenericType = TypeUtils.getFirstGenericType(convertClass);
        if (firstGenericType == null) {
            throw new GenericNotFoundException(convertClass);
        }
        if (this.writeConvertMap.putIfAbsent(firstGenericType, writeConverter) != null) {
            throw new ConvertDuplicationException(this, writeConverter);
        }
    }

    @Override
    public void registerReadConverter(ReadConverter<?> readConverter) {
        Class<?> convertClass = readConverter.getClass();
        Class<?> firstGenericType = TypeUtils.getFirstGenericType(convertClass);
        if (firstGenericType == null) {
            throw new GenericNotFoundException(convertClass);
        }
        if (this.readConvertMap.putIfAbsent(firstGenericType, readConverter) != null) {
            throw new ConvertDuplicationException(this, readConverter);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> WriteConverter<V> findWriteConverter(Class<V> clazz) {
        return (WriteConverter<V>) writeConvertMap.get(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> ReadConverter<V> findReadConverter(Class<V> clazz) {
        return (ReadConverter<V>) writeConvertMap.get(clazz);
    }
}

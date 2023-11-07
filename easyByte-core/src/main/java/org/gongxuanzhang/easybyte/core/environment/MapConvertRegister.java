package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.exception.ConvertDuplicationException;
import org.gongxuanzhang.easybyte.core.exception.GenericNotFoundException;
import org.gongxuanzhang.easybyte.core.tool.TypeUtils;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MapConvertRegister implements ConvertRegister {

    private final Map<Class<?>, WriteConverter<?>> writeConvertMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, ReadConverter<?>> readConvertMap = new ConcurrentHashMap<>();


    @Override
    public void registerWriteConverter(WriteConverter<?> writeConverter) {
        Class<?> convertClass = writeConverter.getClass();
        Type[] targetInterfaceGeneric = TypeUtils.getTargetInterfaceGeneric(convertClass, WriteConverter.class);
        if (targetInterfaceGeneric.length != 1 || !(targetInterfaceGeneric[0] instanceof Class)) {
            throw new GenericNotFoundException(convertClass);
        }
        if (this.writeConvertMap.putIfAbsent((Class<?>) targetInterfaceGeneric[0], writeConverter) != null) {
            throw new ConvertDuplicationException(this, writeConverter);
        }
    }

    @Override
    public void registerReadConverter(ReadConverter<?> readConverter) {
        Class<?> convertClass = readConverter.getClass();
        Type[] targetInterfaceGeneric = TypeUtils.getTargetInterfaceGeneric(convertClass, ReadConverter.class);
        if (targetInterfaceGeneric.length != 1 || !(targetInterfaceGeneric[0] instanceof Class)) {
            throw new GenericNotFoundException(convertClass);
        }
        if (this.readConvertMap.putIfAbsent((Class<?>) targetInterfaceGeneric[0], readConverter) != null) {
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
        return (ReadConverter<V>) readConvertMap.get(clazz);
    }

    @Override
    public void removeReadConverter(Class<?> clazz) {
        readConvertMap.remove(clazz);
    }

    @Override
    public void removeWriteConverter(Class<?> clazz) {
        writeConvertMap.remove(clazz);
    }

    @Override
    public void clear() {
        readConvertMap.clear();
        writeConvertMap.clear();
    }

}

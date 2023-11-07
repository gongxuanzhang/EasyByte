package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.converter.read.StringReadConverter;
import org.gongxuanzhang.easybyte.core.converter.write.StringWriteConverter;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Applies to global configuration
 * it is the last item in the configuration
 *
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class GlobalConfig extends BaseConfiguration {

    private final Map<Class<?>, Supplier<WriteConverter<?>>> surefireDynamicWrite = new HashMap<>();
    private final Map<Class<?>, WriteConverter<?>> surefireFixWrite = new HashMap<>();

    private final Map<Class<?>, Supplier<ReadConverter<?>>> surefireDynamicRead = new HashMap<>();
    private final Map<Class<?>, ReadConverter<?>> surefireFixRead = new HashMap<>();


    private GlobalConfig() {
        for (DefaultEnvironment item : DefaultEnvironment.values()) {
            this.setProperty(item.toString(), item.getDefaultValue());
        }
        surefireDynamicWrite.put(String.class, () -> StringWriteConverter.charset(StandardCharsets.UTF_8));
        surefireDynamicRead.put(String.class, () -> StringReadConverter.charset(StandardCharsets.UTF_8));
    }


    @Override
    public void setProperty(String key, String value) {
        throw new UnsupportedOperationException("global config can't update");
    }

    private static volatile GlobalConfig INSTANCE = null;

    public static GlobalConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (GlobalConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GlobalConfig();
                }
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> WriteConverter<V> findWriteConverter(Class<V> clazz) {
        WriteConverter<V> writeConverter = super.findWriteConverter(clazz);
        if (writeConverter != null) {
            return writeConverter;
        }
        WriteConverter<?> fixConverter = surefireFixWrite.get(clazz);
        if (fixConverter != null) {
            return (WriteConverter<V>) fixConverter;
        }
        Supplier<WriteConverter<?>> supplier = surefireDynamicWrite.get(clazz);
        if (supplier != null) {
            return (WriteConverter<V>) supplier.get();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <V> ReadConverter<V> findReadConverter(Class<V> clazz) {
        ReadConverter<V> readConverter = super.findReadConverter(clazz);
        if (readConverter != null) {
            return readConverter;
        }
        ReadConverter<?> fixConverter = surefireFixRead.get(clazz);
        if (fixConverter != null) {
            return (ReadConverter<V>) fixConverter;
        }
        Supplier<ReadConverter<?>> supplier = surefireDynamicRead.get(clazz);
        if (supplier != null) {
            return (ReadConverter<V>) supplier.get();
        }
        return null;
    }


}

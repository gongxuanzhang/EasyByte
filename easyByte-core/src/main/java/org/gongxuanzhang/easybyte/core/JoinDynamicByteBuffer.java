package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.GlobalConfig;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;
import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * delegate to {@link ByteBuffer}.
 * If the put series of methods are executed. Before put check length.
 * If needs grow,open a new {@link ByteBuffer} and slice from old {@link ByteBuffer}.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public class JoinDynamicByteBuffer extends AbstractDynamicByteBuffer {

    private static final int JOG = 1 << 10;

    private static final int STRIDE = JOG << 4;

    private final GlobalConfig globalConfig;

    private ObjectConfig objectConfig;


    JoinDynamicByteBuffer() {
        super();
        this.objectConfig = null;
        this.globalConfig = GlobalConfig.getInstance();
    }

    JoinDynamicByteBuffer(ObjectConfig objectConfig) {
        super();
        this.objectConfig = objectConfig;
        this.globalConfig = GlobalConfig.getInstance();
    }

    public JoinDynamicByteBuffer(byte[] bytes) {
        this(bytes, null);
    }

    public JoinDynamicByteBuffer(byte[] bytes, ObjectConfig config) {
        super(bytes);
        this.objectConfig = config;
        this.globalConfig = GlobalConfig.getInstance();
    }


    /**
     * figure out the new capacity.
     *
     * @param needLength will put element length
     **/
    @Override
    protected int newCapacity(int needLength) {
        int expectedCapacity = this.delegateBuffer.capacity() + needLength;
        if (expectedCapacity < STRIDE) {
            return ((expectedCapacity / JOG) + 1) << 10;
        }
        int zeroCount = Integer.numberOfLeadingZeros(expectedCapacity);
        int bitBase = (31 - zeroCount - 2);
        return ((expectedCapacity / (1 << bitBase)) + 1) << bitBase;
    }


    @Override
    public void registerWriteConverter(WriteConverter<?> writeConverter) {
        if (this.objectConfig == null) {
            this.objectConfig = new ObjectConfig();
        }
        this.objectConfig.registerWriteConverter(writeConverter);
    }

    @Override
    public void registerReadConverter(ReadConverter<?> readConverter) {
        if (this.objectConfig == null) {
            this.objectConfig = new ObjectConfig();
        }
        this.objectConfig.registerReadConverter(readConverter);
    }

    @Override
    public <V> WriteConverter<V> findWriteConverter(Class<V> clazz) {
        WriteConverter<V> convert = this.objectConfig == null ? null : this.objectConfig.findWriteConverter(clazz);
        if (convert == null) {
            convert = this.globalConfig.findWriteConverter(clazz);
        }
        if (convert == null) {
            throw new ConverterNotFoundException(clazz);
        }
        return convert;
    }

    @Override
    public <V> ReadConverter<V> findReadConverter(Class<V> clazz) {
        ReadConverter<V> convert = this.objectConfig == null ? null : this.objectConfig.findReadConverter(clazz);
        if (convert == null) {
            convert = this.globalConfig.findReadConverter(clazz);
        }
        if (convert == null) {
            throw new ConverterNotFoundException(clazz);
        }
        return convert;
    }

    @Override
    public void removeReadConverter(Class<?> clazz) {
        if (this.objectConfig != null) {
            objectConfig.removeReadConverter(clazz);
        }
        this.globalConfig.removeReadConverter(clazz);
    }

    @Override
    public void removeWriteConverter(Class<?> clazz) {
        if (this.objectConfig != null) {
            objectConfig.removeWriteConverter(clazz);
        }
        this.globalConfig.removeWriteConverter(clazz);
    }

    @Override
    public void clearRegister() {
        if (this.objectConfig != null) {
            this.objectConfig.clearRegister();
        }
    }

    @Override
    public void setProperty(String key, String value) {
        if (this.objectConfig == null) {
            this.objectConfig = new ObjectConfig();
        }
        this.objectConfig.setProperty(key, value);
    }

    @Override
    public String getProperty(String key) {
        if (this.objectConfig == null) {
            return this.globalConfig.getProperty(key);
        }
        String value = this.objectConfig.getProperty(key);
        if (value != null) {
            return value;
        }
        return this.globalConfig.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        if (this.objectConfig == null) {
            return globalConfig.getProperty(key, defaultValue);
        }
        String value = this.objectConfig.getProperty(key);
        if (value == null) {
            return globalConfig.getProperty(key, defaultValue);
        }
        return value;
    }


    /**
     * object config can't infect global config.
     *
     * @param key removed key
     **/
    @Override
    public void removeProperty(String key) {
        if (this.objectConfig != null) {
            this.objectConfig.removeProperty(key);
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> result = new HashSet<>();
        if (this.objectConfig != null) {
            result.addAll(this.objectConfig.keySet());
        }
        result.addAll(this.globalConfig.keySet());
        return result;
    }

    @Override
    public void clearProperties() {
        if (this.objectConfig != null) {
            this.objectConfig.clearProperties();
        }
    }
}

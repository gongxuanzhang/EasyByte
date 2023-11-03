package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.GlobalConfig;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;
import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;

import java.nio.ByteBuffer;

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
    public void registerReadConverter(WriteConverter<?> writeConverter) {
        if (this.objectConfig == null) {
            this.objectConfig = new ObjectConfig();
        }
        this.objectConfig.registerReadConverter(writeConverter);
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

}

package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.GlobalConfig;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;
import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * delegate to {@link ByteBuffer}.
 * If the put series of methods are executed. Before put check length.
 * If needs grow,open a new {@link ByteBuffer} and slice from old {@link ByteBuffer}.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public class JoinDynamicByteBuffer implements DynamicByteBuffer {

    private static final int STRIDE = 1024 * 16;

    private static final int JOG = 1024;


    volatile ByteBuffer delegateBuffer;

    private final GlobalConfig globalConfig;

    private ObjectConfig objectConfig;


    JoinDynamicByteBuffer() {
        this(null);
    }

    JoinDynamicByteBuffer(ObjectConfig objectConfig) {
        this.objectConfig = objectConfig;
        this.globalConfig = GlobalConfig.getInstance();
        this.delegateBuffer = ByteBuffer.allocate(1024);
    }


    @Override
    public DynamicByteBuffer put(byte b) {
        checkLength(Byte.BYTES);
        this.delegateBuffer.put(b);
        return this;
    }


    @Override
    public DynamicByteBuffer put(byte[] bytes) {
        checkLength(bytes.length);
        this.delegateBuffer.put(bytes);
        return this;
    }

    @Override
    public DynamicByteBuffer putShort(short s) {
        checkLength(Short.BYTES);
        this.delegateBuffer.putShort(s);
        return this;
    }


    @Override
    public DynamicByteBuffer putInt(int i) {
        checkLength(Integer.BYTES);
        this.delegateBuffer.putInt(i);
        return this;
    }

    @Override
    public DynamicByteBuffer putLong(long l) {
        checkLength(Long.BYTES);
        this.delegateBuffer.putLong(l);
        return this;
    }

    @Override
    public DynamicByteBuffer putFloat(float f) {
        checkLength(Float.BYTES);
        this.delegateBuffer.putFloat(f);
        return this;
    }

    @Override
    public DynamicByteBuffer putDouble(double d) {
        checkLength(Double.BYTES);
        this.delegateBuffer.putDouble(d);
        return this;
    }

    @Override
    public DynamicByteBuffer putChar(char c) {
        checkLength(Character.BYTES);
        this.delegateBuffer.putChar(c);
        return this;
    }


    @Override
    public byte get() {
        return this.delegateBuffer.get();
    }

    @Override
    public void get(byte[] container) {
        this.delegateBuffer.get(container);
    }


    @Override
    public char getChar() {
        return delegateBuffer.getChar();
    }

    @Override
    public short getShort() {
        return delegateBuffer.getShort();
    }

    @Override
    public int getInt() {
        return delegateBuffer.getInt();
    }

    @Override
    public long getLong() {
        return delegateBuffer.getLong();
    }

    @Override
    public float getFloat() {
        return delegateBuffer.getFloat();
    }

    @Override
    public double getDouble() {
        return delegateBuffer.getDouble();
    }


    @Override
    public <K, V> Map<K, V> getMap(Class<K> keyClazz, Class<V> valueClazz) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> getMap(Class<K> keyClazz, WriteConverter<V> valueConverter) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> getMap(WriteConverter<K> keyConverter, Class<V> valueClazz) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> getMap(WriteConverter<K> keyConvert, WriteConverter<V> valueConvert) {
        return null;
    }


    @Override
    public <K> K getObject(ReadConverter<K> convert) {
        return null;
    }


    private void checkLength(int addLength) {
        if (this.delegateBuffer.remaining() < addLength) {
            ByteBuffer buffer = ByteBuffer.allocate(newCapacity(addLength));
            buffer.put(this.delegateBuffer);
            this.delegateBuffer = buffer;
        }
    }


    /**
     * figure out the new capacity.
     *
     * @param needLength will put element length
     **/
    private int newCapacity(int needLength) {
        if (needLength < JOG) {
            return this.delegateBuffer.capacity() + JOG;
        }
        if (needLength < STRIDE) {
            return this.delegateBuffer.capacity() + ((needLength / JOG) + 1) << 10;
        }
        return this.delegateBuffer.capacity() + ((needLength / STRIDE) + 1) << 10;
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

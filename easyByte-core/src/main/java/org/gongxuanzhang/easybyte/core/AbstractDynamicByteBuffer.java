package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.DefaultEnvironment;
import org.gongxuanzhang.easybyte.core.exception.EncodeException;
import org.gongxuanzhang.easybyte.core.tool.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides a skeletal implementation of the {@link DynamicByteBuffer}
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public abstract class AbstractDynamicByteBuffer implements DynamicByteBuffer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDynamicByteBuffer.class);

    protected volatile ByteBuffer delegateBuffer;

    protected int readPosition = 0;


    protected AbstractDynamicByteBuffer() {
        delegateBuffer = ByteBuffer.allocate(16);
    }

    protected AbstractDynamicByteBuffer(byte[] bytes) {
        delegateBuffer = ByteBuffer.wrap(bytes);
        this.delegateBuffer.limit(bytes.length);
    }


    /**
     * when added element will spill the buffer
     * figure out the new capacity.
     *
     * @param elementLength element length for added
     * @return new length that can contain the element
     **/
    protected abstract int newCapacity(int elementLength);

    /**
     * before append element should check element length.
     *
     * @param elementLength element length for added
     **/
    protected void checkLength(int elementLength) {
        if (this.delegateBuffer.remaining() < elementLength) {
            ByteBuffer buffer = ByteBuffer.allocate(newCapacity(elementLength));
            this.delegateBuffer.flip();
            buffer.put(this.delegateBuffer);
            this.delegateBuffer.clear();
            if (LOG.isDebugEnabled()) {
                LOG.debug("grow is a buffer of length {},expanded by {} lengths", buffer.capacity(),
                        buffer.capacity() - this.delegateBuffer.capacity());
            }
            this.delegateBuffer = buffer;
        }
    }

    @Override
    public DynamicByteBuffer append(byte b) {
        checkLength(Byte.BYTES);
        this.delegateBuffer.put(b);
        return this;
    }


    @Override
    public DynamicByteBuffer append(byte[] bytes) {
        checkLength(bytes.length);
        this.delegateBuffer.put(bytes);
        return this;
    }

    @Override
    public DynamicByteBuffer appendShort(short s) {
        checkLength(Short.BYTES);
        this.delegateBuffer.putShort(s);
        return this;
    }


    @Override
    public DynamicByteBuffer appendInt(int i) {
        checkLength(Integer.BYTES);
        this.delegateBuffer.putInt(i);
        return this;
    }

    @Override
    public DynamicByteBuffer appendLong(long l) {
        checkLength(Long.BYTES);
        this.delegateBuffer.putLong(l);
        return this;
    }

    @Override
    public DynamicByteBuffer appendFloat(float f) {
        checkLength(Float.BYTES);
        this.delegateBuffer.putFloat(f);
        return this;
    }

    @Override
    public DynamicByteBuffer appendDouble(double d) {
        checkLength(Double.BYTES);
        this.delegateBuffer.putDouble(d);
        return this;
    }

    @Override
    public DynamicByteBuffer appendChar(char c) {
        checkLength(Character.BYTES);
        this.delegateBuffer.putChar(c);
        return this;
    }

    @Override
    public DynamicByteBuffer appendBoolean(boolean bool) {
        if (bool) {
            return this.append((byte) 1);
        }
        return this.append((byte) 0);
    }


    @Override
    public byte get() {
        byte result = this.delegateBuffer.get(readPosition);
        offsetReadPosition(Byte.BYTES);
        return result;
    }

    @Override
    public void get(byte[] container) {
        this.delegateBuffer.position(this.readPosition);
        this.delegateBuffer.get(container);
        offsetReadPosition(container.length);
        this.delegateBuffer.position(this.delegateBuffer.limit());
    }


    @Override
    public char getChar() {
        char result = delegateBuffer.getChar(readPosition);
        offsetReadPosition(Character.BYTES);
        return result;
    }

    @Override
    public short getShort() {
        short result = delegateBuffer.getShort(readPosition);
        offsetReadPosition(Short.BYTES);
        return result;
    }

    @Override
    public int getInt() {
        int result = delegateBuffer.getInt(readPosition);
        offsetReadPosition(Integer.BYTES);
        return result;
    }

    @Override
    public long getLong() {
        long result = delegateBuffer.getLong(readPosition);
        offsetReadPosition(Long.BYTES);
        return result;
    }

    @Override
    public float getFloat() {
        float result = delegateBuffer.getFloat(readPosition);
        offsetReadPosition(Float.BYTES);
        return result;
    }

    @Override
    public double getDouble() {
        double result = delegateBuffer.getDouble(readPosition);
        offsetReadPosition(Double.BYTES);
        return result;
    }

    @Override
    public byte[] getLength(int length) {
        byte[] container = new byte[length];
        this.get(container);
        return container;
    }

    @Override
    public boolean getBoolean() {
        return get() != 0;
    }

    @Override
    public DynamicByteBuffer appendString(String s) {
        if (s == null || s.isEmpty()) {
            return appendInt(0);
        }
        return appendString(s, string -> {
            try {
                return string.getBytes(getProperty(DefaultEnvironment.STRING_CHARSET.toString()));
            } catch (UnsupportedEncodingException e) {
                throw new EncodeException();
            }
        });
    }

    @Override
    public DynamicByteBuffer appendString(String s, WriteConverter<String> convert) {
        if (s == null || s.isEmpty()) {
            return appendInt(0);
        }
        byte[] stringBytes = convert.toBytes(s);
        appendInt(stringBytes.length);
        return append(stringBytes);
    }

    @Override
    public String getString(ReadConverter<String> convert) {
        int stringLength = getInt();
        if (stringLength == 0) {
            return null;
        }
        byte[] length = getLength(stringLength);
        return convert.toObject(length);
    }

    @Override
    public String getString() {
        int stringLength = getInt();
        if (stringLength == 0) {
            return null;
        }
        byte[] stringBytes = getLength(stringLength);
        ReadConverter<String> convert = bytes -> {
            String charset = getProperty(DefaultEnvironment.STRING_CHARSET.toString());
            try {
                return new String(bytes, charset);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        };
        return convert.toObject(stringBytes);
    }

    @Override
    public <K> K getObject(ReadConverter<K> convert) {
        int objectLength = getInt();
        byte[] objectBytes = getLength(objectLength);
        return convert.toObject(objectBytes);
    }

    @Override
    public <K> K getObject(Class<K> clazz) {
        return getObject(findReadConverter(clazz));
    }

    /**
     * implement from the parent interface
     *
     * @param keyClazz   key class used by find converter
     * @param valueClazz value class used by find converter
     * @return {@link  DynamicByteBuffer#getMap(ReadConverter, ReadConverter)}
     */
    @Override
    public <K, V> Map<K, V> getMap(Class<K> keyClazz, Class<V> valueClazz) {
        return getMap(findReadConverter(keyClazz), findReadConverter(valueClazz));
    }


    /**
     * implement from the parent interface
     *
     * @param keyClazz       key class used by find converter
     * @param valueConverter special value converter
     * @return {@link  DynamicByteBuffer#getMap(ReadConverter, ReadConverter)}
     */
    @Override
    public <K, V> Map<K, V> getMap(Class<K> keyClazz, ReadConverter<V> valueConverter) {
        return getMap(findReadConverter(keyClazz), valueConverter);
    }


    /**
     * implement from the parent interface
     *
     * @param keyConverter key converter
     * @param valueClazz   value converter
     * @return {@link  DynamicByteBuffer#getMap(ReadConverter, ReadConverter)}
     **/
    @Override
    public <K, V> Map<K, V> getMap(ReadConverter<K> keyConverter, Class<V> valueClazz) {
        return getMap(keyConverter, findReadConverter(valueClazz));
    }

    /**
     * the method that really read byte from buffer
     *
     * @param keyConvert   key class used by find converter
     * @param valueConvert value class used by find converter
     * @return {@link  MapByteBuffer#getMap(ReadConverter, ReadConverter)}
     */
    @Override
    public <K, V> Map<K, V> getMap(ReadConverter<K> keyConvert, ReadConverter<V> valueConvert) {
        int mapSize = getInt();
        Map<K, V> result = new HashMap<>(mapSize / 3 * 4 + 1);
        for (int i = 0; i < mapSize; i++) {
            int keyLength = getInt();
            byte[] keyBytes = getLength(keyLength);
            K key = keyConvert.toObject(keyBytes);
            int valueLength = getInt();
            byte[] valueBytes = getLength(valueLength);
            V value = valueConvert.toObject(valueBytes);
            result.put(key, value);
        }
        return result;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public DynamicByteBuffer appendMap(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return this.appendInt(0);
        }
        WriteConverter keyConverter = null;
        WriteConverter valueConverter = null;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            //  key must not null
            Object key = entry.getKey();
            if (keyConverter == null) {
                keyConverter = findWriteConverter(key.getClass());
            }
            byte[] keyBytes = keyConverter.toBytes(key);
            appendInt(keyBytes.length);
            append(keyBytes);
            Object value = entry.getValue();
            if (value == null) {
                appendInt(0);
                continue;
            }
            if (valueConverter == null) {
                valueConverter = findWriteConverter(value.getClass());
            }
            byte[] valueBytes = valueConverter.toBytes(value);
            appendInt(valueBytes.length);
            append(valueBytes);
        }
        return this;
    }

    @Override
    public <K, V> MapByteBuffer appendMap(Map<K, V> map, WriteConverter<K> keyConverter,
                                          WriteConverter<V> valueConverter) {
        if (map == null || map.isEmpty()) {
            return appendInt(0);
        }
        appendInt(map.size());
        map.forEach((k, v) -> {
            byte[] keyBytes = keyConverter.toBytes(k);
            appendInt(keyBytes.length);
            append(keyBytes);
            if (v == null) {
                appendInt(0);
                return;
            }
            byte[] valueBytes = valueConverter.toBytes(v);
            appendInt(valueBytes.length);
            append(valueBytes);
        });
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> MapByteBuffer appendMap(Map<K, V> map, WriteConverter<V> valueConverter) {
        if (map == null || map.isEmpty()) {
            return this.appendInt(0);
        }
        WriteConverter<K> keyConverter = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            //  key must not null
            K key = entry.getKey();
            if (keyConverter == null) {
                keyConverter = findWriteConverter((Class<K>) key.getClass());
            }
            byte[] keyBytes = keyConverter.toBytes(key);
            appendInt(keyBytes.length);
            append(keyBytes);
            V value = entry.getValue();
            if (value == null) {
                appendInt(0);
                continue;
            }
            byte[] valueBytes = valueConverter.toBytes(value);
            appendInt(valueBytes.length);
            append(valueBytes);
        }
        return this;
    }

    /**
     * implementation that follow the principle of {@link CollectionByteBuffer#appendCollection(Collection)}
     *
     * @param clazz {@link CollectionByteBuffer#getCollection(Class)}
     * @return an ArrayList
     * @see CollectionByteBuffer#getCollection(Class)
     **/
    @Override
    public <V> List<V> getCollection(Class<V> clazz) {
        ReadConverter<V> readConvert = findReadConverter(clazz);
        return getCollection(readConvert);
    }

    /**
     * implementation that follow the principle of {@link CollectionByteBuffer#appendCollection(Collection)}
     *
     * @param convert {@link CollectionByteBuffer#getCollection(ReadConverter)}
     * @return an ArrayList
     * @see CollectionByteBuffer#getCollection(Class)
     **/
    @Override
    public <V> List<V> getCollection(ReadConverter<V> convert) {
        int collectionSize = getInt();
        List<V> result = new ArrayList<>(collectionSize);
        for (int i = 0; i < collectionSize; i++) {
            int itemLength = getInt();
            byte[] itemBytes = getLength(itemLength);
            V item = convert.toObject(itemBytes);
            result.add(item);
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public DynamicByteBuffer appendCollection(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return appendInt(0);
        }
        Class<?> genericType = TypeUtils.getFirstGenericType(collection.getClass());
        WriteConverter writeConvert = findWriteConverter(genericType);
        return this.appendCollection(collection, writeConvert);
    }

    @Override
    public <V> DynamicByteBuffer appendCollection(Collection<V> collection, WriteConverter<V> convert) {
        if (collection == null || collection.isEmpty()) {
            return appendInt(0);
        }
        appendInt(collection.size());
        collection.forEach(item -> {
            byte[] itemBytes = convert.toBytes(item);
            appendInt(itemBytes.length);
            append(itemBytes);
        });
        return this;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public DynamicByteBuffer appendObject(Object o) {
        WriteConverter<?> writeConvert = findWriteConverter(o.getClass());
        return this.appendObject(o, (WriteConverter) writeConvert);
    }

    @Override
    public <K> DynamicByteBuffer appendObject(K object, WriteConverter<K> converter) {
        byte[] objectBytes = converter.toBytes(object);
        appendInt(objectBytes.length);
        append(objectBytes);
        return this;
    }


    @Override
    public byte[] toBytes() {
        return Arrays.copyOf(this.delegateBuffer.array(), this.delegateBuffer.position());
    }

    protected void offsetReadPosition(int offset) {
        this.readPosition += offset;
    }
}

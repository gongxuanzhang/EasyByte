package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.tool.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected AbstractDynamicByteBuffer() {
        delegateBuffer = ByteBuffer.allocate(16);
    }

    protected AbstractDynamicByteBuffer(byte[] bytes) {
        delegateBuffer = ByteBuffer.wrap(bytes);
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
     * before put element should check element length.
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
    public DynamicByteBuffer putBoolean(boolean bool) {
        if (bool) {
            return this.put((byte) 1);
        }
        return this.put((byte) 0);
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
    public DynamicByteBuffer putMap(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return this.putInt(0);
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
            putInt(keyBytes.length);
            put(keyBytes);
            Object value = entry.getValue();
            if (value == null) {
                putInt(0);
                continue;
            }
            if (valueConverter == null) {
                valueConverter = findWriteConverter(value.getClass());
            }
            byte[] valueBytes = valueConverter.toBytes(value);
            putInt(valueBytes.length);
            put(valueBytes);
        }
        return this;
    }

    @Override
    public <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConverter<K> keyConverter,
                                       WriteConverter<V> valueConverter) {
        if (map == null || map.isEmpty()) {
            return putInt(0);
        }
        putInt(map.size());
        map.forEach((k, v) -> {
            byte[] keyBytes = keyConverter.toBytes(k);
            putInt(keyBytes.length);
            put(keyBytes);
            if (v == null) {
                putInt(0);
                return;
            }
            byte[] valueBytes = valueConverter.toBytes(v);
            putInt(valueBytes.length);
            put(valueBytes);
        });
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConverter<V> valueConverter) {
        if (map == null || map.isEmpty()) {
            return this.putInt(0);
        }
        WriteConverter<K> keyConverter = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            //  key must not null
            K key = entry.getKey();
            if (keyConverter == null) {
                keyConverter = findWriteConverter((Class<K>) key.getClass());
            }
            byte[] keyBytes = keyConverter.toBytes(key);
            putInt(keyBytes.length);
            put(keyBytes);
            V value = entry.getValue();
            if (value == null) {
                putInt(0);
                continue;
            }
            byte[] valueBytes = valueConverter.toBytes(value);
            putInt(valueBytes.length);
            put(valueBytes);
        }
        return this;
    }

    /**
     * implementation that follow the principle of {@link CollectionByteBuffer#putCollection(Collection)}
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
     * implementation that follow the principle of {@link CollectionByteBuffer#putCollection(Collection)}
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
    public DynamicByteBuffer putCollection(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return putInt(0);
        }
        Class<?> genericType = TypeUtils.getFirstGenericType(collection.getClass());
        WriteConverter writeConvert = findWriteConverter(genericType);
        return putCollection(collection, writeConvert);
    }

    @Override
    public <V> DynamicByteBuffer putCollection(Collection<V> collection, WriteConverter<V> convert) {
        if (collection == null || collection.isEmpty()) {
            return putInt(0);
        }
        putInt(collection.size());
        collection.forEach(item -> {
            byte[] itemBytes = convert.toBytes(item);
            putInt(itemBytes.length);
            put(itemBytes);
        });
        return this;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public DynamicByteBuffer putObject(Object o) {
        WriteConverter<?> writeConvert = findWriteConverter(o.getClass());
        return this.putObject(o, (WriteConverter) writeConvert);
    }

    @Override
    public <K> DynamicByteBuffer putObject(K object, WriteConverter<K> converter) {
        byte[] objectBytes = converter.toBytes(object);
        putInt(objectBytes.length);
        put(objectBytes);
        return this;
    }


    @Override
    public byte[] toBytes() {
        return Arrays.copyOf(this.delegateBuffer.array(), this.delegateBuffer.position());
    }
}

package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;
import org.gongxuanzhang.easybyte.core.tool.TypeUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * different from {@link java.nio.ByteBuffer}, it can dynamically capacity.
 * <p>
 * only sequential put is supported, so it is not suitable for random access.
 *
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public interface DynamicByteBuffer extends CollectionByteBuffer, MapByteBuffer, ReferenceByteBuffer, ConvertRegister {


    /**
     * get a dynamicByteBuffer instance
     **/
    default DynamicByteBuffer open() {
        return new JoinDynamicByteBuffer();
    }

    /**
     * get a dynamicByteBuffer instance
     *
     * @param config session config
     **/
    default DynamicByteBuffer open(ObjectConfig config) {
        return new JoinDynamicByteBuffer(config);
    }

    /**
     * put a byte,similar to {@link ByteBuffer#put(byte)}.
     *
     * @param b a byte
     * @return this
     **/
    DynamicByteBuffer put(byte b);


    /**
     * put a byte array ,similar to {@link ByteBuffer#put(byte[])}
     *
     * @param bytes byte array
     * @return this
     **/
    DynamicByteBuffer put(byte[] bytes);

    /**
     * put a short,similar to {@link ByteBuffer#putShort(short)}
     *
     * @param s a short
     * @return this
     **/
    DynamicByteBuffer putShort(short s);

    /**
     * put a int,similar to {@link ByteBuffer#putInt(int)}
     *
     * @param i a int
     * @return this
     **/
    DynamicByteBuffer putInt(int i);


    /**
     * put a long ,similar to {@link ByteBuffer#putLong(long)}
     *
     * @param l a long
     * @return this
     **/
    DynamicByteBuffer putLong(long l);

    /**
     * put a float,similar to {@link ByteBuffer#putFloat(float)}
     *
     * @param f a float
     * @return this
     **/
    DynamicByteBuffer putFloat(float f);

    /**
     * put a double,similar to {@link ByteBuffer#putDouble(double)}
     *
     * @param d a double
     * @return this
     **/
    DynamicByteBuffer putDouble(double d);

    /**
     * put a char,similar to {@link ByteBuffer#putChar(char)}
     *
     * @param c a char
     * @return this
     **/
    DynamicByteBuffer putChar(char c);

    /**
     * put a boolean as byte
     *
     * @param bool a boolean
     * @return this
     **/
    default DynamicByteBuffer putBoolean(boolean bool) {
        if (bool) {
            return this.put((byte) 1);
        }
        return this.put((byte) 0);
    }


    /**
     * Read a byte from the buffer and advance the position, similar to {@link ByteBuffer#get()}.
     *
     * @return The byte in the buffer.
     **/
    byte get();

    /**
     * read a byte array input to container, similar to {@link ByteBuffer#get(byte[])}
     *
     * @param container {@link ByteBuffer#get(byte[])} param
     **/
    void get(byte[] container);

    /**
     * read a special length from buffer.
     * if buffer remain  less than length,it will throw error
     *
     * @return a byte array filled from buffer,length is param
     **/
    default byte[] getLength(int length) {
        byte[] container = new byte[length];
        this.get(container);
        return container;
    }

    /**
     * Read a short from the buffer and advance the position, similar to {@link ByteBuffer#getShort()}.
     *
     * @return The short in the buffer.
     **/
    short getShort();

    /**
     * Read an integer from the buffer and advance the position, similar to {@link ByteBuffer#getInt()}.
     *
     * @return The integer in the buffer.
     **/
    int getInt();

    /**
     * Read a long from the buffer and advance the position, similar to {@link ByteBuffer#getLong()}.
     *
     * @return The long in the buffer.
     **/
    long getLong();

    /**
     * Read a float from the buffer and advance the position, similar to {@link ByteBuffer#getFloat()}.
     *
     * @return The float in the buffer.
     **/
    float getFloat();

    /**
     * Read a double from the buffer and advance the position, similar to {@link ByteBuffer#getDouble()}.
     *
     * @return The double in the buffer.
     **/
    double getDouble();

    /**
     * Read a boolean from the buffer and advance the position.
     * boolean as byte to save that zero is false and non-zero is true.
     **/
    default boolean getBoolean() {
        return get() != 0;
    }

    /**
     * Read a double from the buffer and advance the position, similar to {@link ByteBuffer#getChar()} .
     *
     * @return the char in the buffer
     **/
    char getChar();

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param collection {@link CollectionByteBuffer#putCollection(Collection)}
     * @return this
     **/
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    default DynamicByteBuffer putCollection(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return putInt(0);
        }
        Class<?> genericType = TypeUtils.getFirstGenericType(collection.getClass());
        WriteConverter writeConvert = findWriteConverter(genericType);
        return putCollection(collection, writeConvert);
    }

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param collection {@link CollectionByteBuffer#putCollection(Collection, WriteConverter)}
     * @param convert    {@link CollectionByteBuffer#putCollection(Collection, WriteConverter)}
     * @return this
     **/
    @Override
    default <V> DynamicByteBuffer putCollection(Collection<V> collection, WriteConverter<V> convert) {
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

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map {@link MapByteBuffer#putMap(Map)}
     * @return this
     **/
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    default MapByteBuffer putMap(Map<?, ?> map) {
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

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map            {@link MapByteBuffer#putMap(Map, WriteConverter, WriteConverter)}
     * @param keyConverter   {@link MapByteBuffer#putMap(Map, WriteConverter, WriteConverter)}
     * @param valueConverter {@link MapByteBuffer#putMap(Map, WriteConverter, WriteConverter)}
     * @return this
     **/
    @Override
    default <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConverter<K> keyConverter,
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

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map            {@link MapByteBuffer#putMap(Map, WriteConverter)}
     * @param valueConverter {@link MapByteBuffer#putMap(Map, WriteConverter)}
     * @return this
     **/
    @SuppressWarnings("unchecked")
    @Override
    default <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConverter<V> valueConverter) {
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
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param o {@link ReferenceByteBuffer#putObject(Object)}
     * @return this
     **/
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    default DynamicByteBuffer putObject(Object o) {
        WriteConverter<?> writeConvert = findWriteConverter(o.getClass());
        return this.putObject(o, (WriteConverter) writeConvert);
    }

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param object    {@link ReferenceByteBuffer#putObject(Object, WriteConverter)}
     * @param converter {@link ReferenceByteBuffer#putObject(Object, WriteConverter)}
     * @return this
     **/
    @Override
    default <K> DynamicByteBuffer putObject(K object, WriteConverter<K> converter) {
        byte[] objectBytes = converter.toBytes(object);
        putInt(objectBytes.length);
        put(objectBytes);
        return this;
    }

    /**
     * default implementation that follow the principle of {@link CollectionByteBuffer#putCollection(Collection)}
     *
     * @param clazz {@link CollectionByteBuffer#getCollection(Class)}
     * @return an ArrayList
     * @see CollectionByteBuffer#getCollection(Class)
     **/
    @Override
    default <V> List<V> getCollection(Class<V> clazz) {
        ReadConverter<V> readConvert = findReadConverter(clazz);
        return getCollection(readConvert);
    }


    /**
     * default implementation that follow the principle of {@link CollectionByteBuffer#putCollection(Collection)}
     *
     * @param convert {@link CollectionByteBuffer#getCollection(ReadConverter)}
     * @return an ArrayList
     * @see CollectionByteBuffer#getCollection(Class)
     **/
    @Override
    default <V> List<V> getCollection(ReadConverter<V> convert) {
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


}

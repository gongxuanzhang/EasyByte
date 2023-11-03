package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.EasyByteConfiguration;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;

/**
 * Like {@link  ByteBuffer}, but it can dynamically capacity.
 * <p>
 * only sequential put is supported,called append, so it is not suitable for random access.
 * <p>
 * All methods prefixed with append that append element at the end of the buffer.
 * Buffer capacity will extended when  buffer remain less than element length.
 * <p>
 * This interface specifies read and append are completely independent.
 * Additionally,the interface is not thread-safe.
 *
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public interface DynamicByteBuffer extends CollectionByteBuffer, MapByteBuffer, StringByteBuffer, ReferenceByteBuffer
        , EasyByteConfiguration, ByteWrapper {


    /**
     * get a dynamicByteBuffer instance,similar to {@link ByteBuffer#allocate(int)}
     *
     * @return new dynamicByteBuffer
     **/
    static DynamicByteBuffer allocate() {
        return new JoinDynamicByteBuffer();
    }


    /**
     * get a dynamicByteBuffer instance with config,similar to {@link ByteBuffer#allocate(int)}
     *
     * @param config session config
     * @return new dynamicByteBuffer
     **/
    static DynamicByteBuffer allocate(ObjectConfig config) {
        return new JoinDynamicByteBuffer(config);
    }

    /**
     * wrap a byte array into buffer,similar to {@link ByteBuffer#wrap(byte[])}
     *
     * @param bytes The array that will back this buffer
     * @return new dynamicByteBuffer contain bytes
     **/
    static DynamicByteBuffer wrap(byte[] bytes) {
        return new JoinDynamicByteBuffer(bytes);
    }

    /**
     * wrap a byte array into buffer with config,similar to {@link ByteBuffer#wrap(byte[])}
     *
     * @param bytes  The array that will back this buffer
     * @param config config
     * @return new dynamicByteBuffer contain bytes
     **/
    static DynamicByteBuffer wrap(byte[] bytes, ObjectConfig config) {
        return new JoinDynamicByteBuffer(bytes, config);
    }


    /**
     * append a byte,similar to {@link ByteBuffer#put(byte)}.
     *
     * @param b a byte
     * @return this
     **/
    DynamicByteBuffer append(byte b);


    /**
     * append a byte array ,similar to {@link ByteBuffer#put(byte[])}
     *
     * @param bytes byte array
     * @return this
     **/
    DynamicByteBuffer append(byte[] bytes);

    /**
     * append a short,similar to {@link ByteBuffer#putShort(short)}
     *
     * @param s a short
     * @return this
     **/
    DynamicByteBuffer appendShort(short s);

    /**
     * append a int,similar to {@link ByteBuffer#putInt(int)}
     *
     * @param i a int
     * @return this
     **/
    DynamicByteBuffer appendInt(int i);


    /**
     * append a long ,similar to {@link ByteBuffer#putLong(long)}
     *
     * @param l a long
     * @return this
     **/
    DynamicByteBuffer appendLong(long l);

    /**
     * append a float,similar to {@link ByteBuffer#putFloat(float)}
     *
     * @param f a float
     * @return this
     **/
    DynamicByteBuffer appendFloat(float f);

    /**
     * append a double,similar to {@link ByteBuffer#putDouble(double)}
     *
     * @param d a double
     * @return this
     **/
    DynamicByteBuffer appendDouble(double d);

    /**
     * append a char,similar to {@link ByteBuffer#putChar(char)}
     *
     * @param c a char
     * @return this
     **/
    DynamicByteBuffer appendChar(char c);

    /**
     * append a boolean as byte
     *
     * @param bool a boolean
     * @return this
     **/
    DynamicByteBuffer appendBoolean(boolean bool);

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
     * @throws BufferUnderflowException If there are fewer than {@code container} bytes
     *                                  remaining in this buffer
     **/
    void get(byte[] container);

    /**
     * read a special length from buffer.
     * if buffer remain  less than length,it will throw error
     *
     * @param length read length of byte
     * @return a byte array filled from buffer,length is param
     **/
    byte[] getLength(int length);

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
    boolean getBoolean();

    /**
     * Read a double from the buffer and advance the position, similar to {@link ByteBuffer#getChar()} .
     *
     * @return the char in the buffer
     **/
    char getChar();

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param collection {@link CollectionByteBuffer#appendCollection(Collection)}
     * @return this
     **/
    @Override
    DynamicByteBuffer appendCollection(Collection<?> collection);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param collection {@link CollectionByteBuffer#appendCollection(Collection, WriteConverter)}
     * @param convert    {@link CollectionByteBuffer#appendCollection(Collection, WriteConverter)}
     * @return this
     **/
    @Override
    <V> DynamicByteBuffer appendCollection(Collection<V> collection, WriteConverter<V> convert);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map {@link MapByteBuffer#appendMap(Map)}
     * @return this
     **/
    @Override
    DynamicByteBuffer appendMap(Map<?, ?> map);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map            {@link MapByteBuffer#appendMap(Map, WriteConverter, WriteConverter)}
     * @param keyConverter   {@link MapByteBuffer#appendMap(Map, WriteConverter, WriteConverter)}
     * @param valueConverter {@link MapByteBuffer#appendMap(Map, WriteConverter, WriteConverter)}
     * @return this
     **/
    @Override
    <K, V> MapByteBuffer appendMap(Map<K, V> map, WriteConverter<K> keyConverter, WriteConverter<V> valueConverter);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map            {@link MapByteBuffer#appendMap(Map, WriteConverter)}
     * @param valueConverter {@link MapByteBuffer#appendMap(Map, WriteConverter)}
     * @return this
     **/
    @Override
    <K, V> MapByteBuffer appendMap(Map<K, V> map, WriteConverter<V> valueConverter);


    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param o {@link ReferenceByteBuffer#appendObject(Object)}
     * @return this
     **/
    @Override
    DynamicByteBuffer appendObject(Object o);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param object    {@link ReferenceByteBuffer#appendObject(Object, WriteConverter)}
     * @param converter {@link ReferenceByteBuffer#appendObject(Object, WriteConverter)}
     * @return this
     **/
    @Override
    <K> DynamicByteBuffer appendObject(K object, WriteConverter<K> converter);


    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param s {@link StringByteBuffer#appendString(String)}
     * @return this
     **/
    @Override
    DynamicByteBuffer appendString(String s);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param s       {@link StringByteBuffer#appendString(String, WriteConverter)}
     * @param convert {@link StringByteBuffer#appendString(String, WriteConverter)}
     * @return this
     **/
    @Override
    DynamicByteBuffer appendString(String s, WriteConverter<String> convert);

}

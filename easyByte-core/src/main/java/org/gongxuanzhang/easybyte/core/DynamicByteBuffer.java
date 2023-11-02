package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Collection;
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
    DynamicByteBuffer putBoolean(boolean bool);

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
     * @param collection {@link CollectionByteBuffer#putCollection(Collection)}
     * @return this
     **/
    @Override
    DynamicByteBuffer putCollection(Collection<?> collection);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param collection {@link CollectionByteBuffer#putCollection(Collection, WriteConverter)}
     * @param convert    {@link CollectionByteBuffer#putCollection(Collection, WriteConverter)}
     * @return this
     **/
    @Override
    <V> DynamicByteBuffer putCollection(Collection<V> collection, WriteConverter<V> convert);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map {@link MapByteBuffer#putMap(Map)}
     * @return this
     **/
    @Override
    DynamicByteBuffer putMap(Map<?, ?> map);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map            {@link MapByteBuffer#putMap(Map, WriteConverter, WriteConverter)}
     * @param keyConverter   {@link MapByteBuffer#putMap(Map, WriteConverter, WriteConverter)}
     * @param valueConverter {@link MapByteBuffer#putMap(Map, WriteConverter, WriteConverter)}
     * @return this
     **/
    @Override
    <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConverter<K> keyConverter, WriteConverter<V> valueConverter);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map            {@link MapByteBuffer#putMap(Map, WriteConverter)}
     * @param valueConverter {@link MapByteBuffer#putMap(Map, WriteConverter)}
     * @return this
     **/
    @Override
    <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConverter<V> valueConverter);


    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param o {@link ReferenceByteBuffer#putObject(Object)}
     * @return this
     **/
    @Override
    DynamicByteBuffer putObject(Object o);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param object    {@link ReferenceByteBuffer#putObject(Object, WriteConverter)}
     * @param converter {@link ReferenceByteBuffer#putObject(Object, WriteConverter)}
     * @return this
     **/
    @Override
    <K> DynamicByteBuffer putObject(K object, WriteConverter<K> converter);

}

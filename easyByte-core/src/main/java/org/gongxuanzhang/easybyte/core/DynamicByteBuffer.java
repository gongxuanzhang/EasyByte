package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;

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
public interface DynamicByteBuffer extends CollectionByteBuffer, MapByteBuffer, ReferenceByteBuffer {


    /**
     * get a dynamicByteBuffer instance
     **/
    default DynamicByteBuffer open() {
        return new ArrayDynamicByteBuffer();
    }

    /**
     * get a dynamicByteBuffer instance
     *
     * @param config session config
     **/
    default DynamicByteBuffer open(ObjectConfig config) {
        return new ArrayDynamicByteBuffer(config);
    }

    /**
     * put a byte,similar to {@link ByteBuffer#put(byte)}.
     *
     * @param b a byte
     * @return this
     **/
    DynamicByteBuffer put(byte b);

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
     * put a boolean,similar to {@link ByteBuffer#put}
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
     * @param collection {@link CollectionByteBuffer#putCollection(Collection, WriteConvert)}
     * @param convert    {@link CollectionByteBuffer#putCollection(Collection, WriteConvert)}
     * @return this
     **/
    @Override
    <V> DynamicByteBuffer putCollection(Collection<V> collection, WriteConvert<V> convert);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map {@link MapByteBuffer#putMap(Map)}
     * @return this
     **/
    @Override
    MapByteBuffer putMap(Map<?, ?> map);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map          {@link MapByteBuffer#putMap(Map, WriteConvert, WriteConvert)}
     * @param keyConvert   {@link MapByteBuffer#putMap(Map, WriteConvert, WriteConvert)}
     * @param valueConvert {@link MapByteBuffer#putMap(Map, WriteConvert, WriteConvert)}
     * @return this
     **/
    @Override
    <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConvert<K> keyConvert, WriteConvert<V> valueConvert);

    /**
     * limit return type is {@link DynamicByteBuffer}
     *
     * @param map          {@link MapByteBuffer#putMap(Map, WriteConvert)}
     * @param valueConvert {@link MapByteBuffer#putMap(Map, WriteConvert)}
     * @return this
     **/
    @Override
    <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConvert<V> valueConvert);

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
     * @param object       {@link ReferenceByteBuffer#putObject(Object, WriteConvert)}
     * @param convert {@link ReferenceByteBuffer#putObject(Object, WriteConvert)}
     * @return this
     **/
    @Override
    <K> DynamicByteBuffer putObject(K object, WriteConvert<K> convert);
}

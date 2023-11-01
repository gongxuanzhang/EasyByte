package org.gongxuanzhang.easybyte.core;

import java.util.Map;

/**
 * can put and get map.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface MapByteBuffer {

    /**
     * get a map from buffer.
     * find convert by keyClazz and valueClazz in register.
     *
     * @param keyClazz   key class
     * @param valueClazz value class
     * @return map
     **/
    <K, V> Map<K, V> getMap(Class<K> keyClazz, Class<V> valueClazz);

    /**
     * get a map from buffer.
     * find convert by keyClazz .
     * special value convert
     *
     * @param keyClazz     key class
     * @param valueConvert value convert
     * @return map
     **/
    <K, V> Map<K, V> getMap(Class<K> keyClazz, WriteConvert<V> valueConvert);

    /**
     * get a map from buffer.
     * find convert by valueClazz.
     * special key convert
     *
     * @param keyConvert key convert
     * @param valueClazz value class
     * @return map
     **/
    <K, V> Map<K, V> getMap(WriteConvert<K> keyConvert, Class<V> valueClazz);

    /**
     * get a map from buffer.
     * special key and value convert
     *
     * @param keyConvert   key convert
     * @param valueConvert value convert
     * @return map
     **/
    <K, V> Map<K, V> getMap(WriteConvert<K> keyConvert, WriteConvert<V> valueConvert);


    /**
     * put a map to buffer.
     * if key or value is {@link  ByteWrapper}
     * will invoke {@link ByteWrapper#toBytes()}
     * else will find convert in register.
     * neither will throw {@link org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException}
     *
     * @param map map
     * @return this
     **/
    MapByteBuffer putMap(Map<?, ?> map);

    /**
     * put a map to buffer.
     * special key and value convert to use.
     *
     * @param map          data
     * @param keyConvert   special key convert
     * @param valueConvert special value convert
     * @return this
     **/
    <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConvert<K> keyConvert, WriteConvert<V> valueConvert);

    /**
     * put a map to buffer.
     * special value convert to use because generally key is common type
     *
     * @param map          data
     * @param valueConvert special value convert
     * @return this
     **/
    <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConvert<V> valueConvert);


}

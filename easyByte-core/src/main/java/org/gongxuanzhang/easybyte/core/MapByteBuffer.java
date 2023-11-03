package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;
import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;

import java.util.Map;

/**
 * can append and get map.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface MapByteBuffer extends ConvertRegister {

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
     * @param keyClazz       key class
     * @param valueConverter value convert
     * @return map
     **/
    <K, V> Map<K, V> getMap(Class<K> keyClazz, ReadConverter<V> valueConverter);

    /**
     * get a map from buffer.
     * find convert by valueClazz.
     * special key convert
     *
     * @param keyConverter key convert
     * @param valueClazz   value class
     * @return map
     **/
    <K, V> Map<K, V> getMap(ReadConverter<K> keyConverter, Class<V> valueClazz);

    /**
     * get a map from buffer.
     * special key and value convert
     *
     * @param keyConvert   key convert
     * @param valueConvert value convert
     * @return map
     **/
    <K, V> Map<K, V> getMap(ReadConverter<K> keyConvert, ReadConverter<V> valueConvert);


    /**
     * append a map to buffer.
     * if key or value is {@link  ByteWrapper} will invoke {@link ByteWrapper#toBytes()}
     * else will find converter in register.
     * if map is not empty that easy byte will find converter by the class that first entry key and value .
     * so the items in map is polymorphism please use
     * {@link MapByteBuffer#appendMap(Map, WriteConverter, WriteConverter)} since can't get accurate generic type of map.
     * <p>
     * it maybe throw {@link ConverterNotFoundException} when can't find converter
     *
     * @param map map key not null
     * @return this
     **/
    MapByteBuffer appendMap(Map<?, ?> map);

    /**
     * append a map to buffer.
     * special key and value converter to use.
     *
     * @param map            data
     * @param keyConverter   special key convert
     * @param valueConverter special value convert
     * @return this
     **/
    <K, V> MapByteBuffer appendMap(Map<K, V> map, WriteConverter<K> keyConverter, WriteConverter<V> valueConverter);

    /**
     * append a map to buffer.
     * special value convert to use because generally key is common type
     *
     * @param map          data
     * @param valueConvert special value convert
     * @return this
     **/
    <K, V> MapByteBuffer appendMap(Map<K, V> map, WriteConverter<V> valueConvert);


}

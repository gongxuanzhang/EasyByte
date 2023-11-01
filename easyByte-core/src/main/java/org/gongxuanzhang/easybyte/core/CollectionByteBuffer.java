package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * can put and get collection.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface CollectionByteBuffer {

    /**
     * put a collection
     * the order of puts depends on the order of traversal
     * item in collection will converted to byte array for put buffer
     * primitive type and packing type will invoke corresponding method
     * reference type will find convert or wrapper to invoke method
     * if neither convert nor wrapper can be found,an {@link ConverterNotFoundException} will be throw
     *
     * @param collection use for put
     * @return this
     **/
    DynamicByteBuffer putCollection(Collection<?> collection);

    /**
     * specify a converter to convert item
     * {@link this#putCollection(Collection)}
     *
     * @param collection a collection
     * @param convert    specify convert
     * @return this
     **/
    <V> DynamicByteBuffer putCollection(Collection<V> collection, WriteConvert<V> convert);


    /**
     * get collection in buffer.
     * return the list because the order of traversal in buffer is guaranteed.
     * convert will find in register
     * if can't find,will throw {@link ConverterNotFoundException}
     *
     * @return list
     **/
    <V> List<V> getCollection(Class<V> clazz);

    /**
     * specify a special converter to convert item
     *
     * @param convert not null
     * @return list {@link this#getCollection(Class)}
     **/
    <V> List<V> getCollection(ReadConvert<V> convert);
}

package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;
import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * can append and get collection.
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface CollectionByteBuffer extends ConvertRegister {

    /**
     * append a collection
     * first append collection size and then append collection item 1 size item 1 byte item N size item N byte like follows
     * ┌──────────┬──────┬─────┬─────┬──────┐
     * │collection│item1 │item1│itemN│itemN │
     * │   size   │ size │ byte│ size│ byte │
     * └──────────┴──────┴─────┴─────┴──────┘
     * <p>
     * the order of appends depends on the order of traversal
     * item in collection will converted to byte array for append to buffer
     * primitive type and packing type will invoke corresponding method
     * reference type will find convert or wrapper to invoke method
     * <p>
     * <p>
     * if neither convert nor wrapper can be found,an {@link ConverterNotFoundException} will be throw
     *
     * @param collection use for append
     * @return this
     **/
    DynamicByteBuffer appendCollection(Collection<?> collection);

    /**
     * specify a converter to convert item
     * {@link this#appendCollection(Collection)}
     *
     * @param collection a collection
     * @param convert    specify convert
     * @return this
     **/
    <V> DynamicByteBuffer appendCollection(Collection<V> collection, WriteConverter<V> convert);


    /**
     * get collection in buffer.
     * return the list because the order of traversal in buffer is guaranteed.
     * convert will find in register
     * if can't find,will throw {@link ConverterNotFoundException}
     *
     * @param clazz expect item class
     * @return list
     * @see CollectionByteBuffer#appendCollection(Collection)
     **/
    <V> List<V> getCollection(Class<V> clazz);

    /**
     * specify a special converter to convert item
     *
     * @param convert not null
     * @return list {@link this#getCollection(Class)}
     * @see CollectionByteBuffer#appendCollection(Collection)
     **/
    <V> List<V> getCollection(ReadConverter<V> convert);
}

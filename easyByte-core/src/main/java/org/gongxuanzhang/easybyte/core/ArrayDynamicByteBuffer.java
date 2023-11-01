package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.GlobalConfig;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;
import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class ArrayDynamicByteBuffer implements DynamicByteBuffer {

    private final GlobalConfig globalConfig;

    private ObjectConfig objectConfig;

    ArrayDynamicByteBuffer() {
        this(null);
    }

    ArrayDynamicByteBuffer(ObjectConfig objectConfig) {
        this.objectConfig = objectConfig;
        this.globalConfig = GlobalConfig.getInstance();
    }

    @Override
    public <V> List<V> getCollection(Class<V> clazz) {
        return null;
    }

    @Override
    public <V> List<V> getCollection(ReadConvert<V> convert) {
        return null;
    }

    @Override
    public DynamicByteBuffer put(byte b) {
        return null;
    }

    @Override
    public DynamicByteBuffer putShort(short s) {
        return null;
    }

    @Override
    public DynamicByteBuffer putInt(int i) {
        return null;
    }

    @Override
    public DynamicByteBuffer putLong(long l) {
        return null;
    }

    @Override
    public DynamicByteBuffer putFloat(float f) {
        return null;
    }

    @Override
    public DynamicByteBuffer putDouble(double d) {
        return null;
    }

    @Override
    public DynamicByteBuffer putChar(char c) {
        return null;
    }

    @Override
    public DynamicByteBuffer putBoolean(boolean bool) {
        return null;
    }

    @Override
    public byte get() {
        return 0;
    }

    @Override
    public short getShort() {
        return 0;
    }

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public long getLong() {
        return 0;
    }

    @Override
    public float getFloat() {
        return 0;
    }

    @Override
    public double getDouble() {
        return 0;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public DynamicByteBuffer putCollection(Collection<?> collection) {
        return null;
    }

    @Override
    public <V> DynamicByteBuffer putCollection(Collection<V> collection, WriteConvert<V> convert) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> getMap(Class<K> keyClazz, Class<V> valueClazz) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> getMap(Class<K> keyClazz, WriteConvert<V> valueConvert) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> getMap(WriteConvert<K> keyConvert, Class<V> valueClazz) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> getMap(WriteConvert<K> keyConvert, WriteConvert<V> valueConvert) {
        return null;
    }

    @Override
    public MapByteBuffer putMap(Map<?, ?> map) {
        return null;
    }

    @Override
    public <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConvert<K> keyConvert, WriteConvert<V> valueConvert) {
        return null;
    }

    @Override
    public <K, V> MapByteBuffer putMap(Map<K, V> map, WriteConvert<V> valueConvert) {
        return null;
    }

    @Override
    public <K> K getObject(Class<K> clazz) {
        return null;
    }

    @Override
    public <K> K getObject(ReadConvert<K> convert) {
        return null;
    }

    @Override
    public DynamicByteBuffer putObject(Object o) {
        return null;
    }

    @Override
    public <K> DynamicByteBuffer putObject(K object, WriteConvert<K> convert) {
        return null;
    }

//
//    private <V> WriteConvert<V> findWriteConvert(Class<V> clazz) {
//        WriteConvert<V> convert = this.objectConfig == null ? null : this.objectConfig.findWriteConvert(clazz);
//        if (convert == null) {
//            convert = this.globalConfig.findWriteConvert(clazz);
//        }
//        if (convert == null) {
//            throw new ConverterNotFoundException(clazz);
//        }
//        return convert;
//    }
//
//    private WriteConvert<?> findWriteConvert(Object object) {
//        return findWriteConvert(object.getClass());
//    }
//
//    private <V> ReadConvert<V> findReadConvert(Class<V> clazz) {
//        ReadConvert<V> convert = this.objectConfig == null ? null : this.objectConfig.findReadConvert(clazz);
//        if (convert == null) {
//            convert = this.globalConfig.findReadConvert(clazz);
//        }
//        if (convert == null) {
//            throw new ConverterNotFoundException(clazz);
//        }
//        return convert;
//    }

}

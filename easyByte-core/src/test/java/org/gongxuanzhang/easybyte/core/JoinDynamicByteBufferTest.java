package org.gongxuanzhang.easybyte.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class JoinDynamicByteBufferTest {


    @Test
    void testPutVastDataByteOneByOne() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData();
        for (byte datum : bytes) {
            byteBuffer.put(datum);
        }
        assertArrayEquals(bytes, byteBuffer.toBytes());
    }

    @Test
    void testPutVastDataShortOneByOne() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData();
        for (byte datum : bytes) {
            byteBuffer.put(datum);
        }
        assertArrayEquals(bytes, byteBuffer.toBytes());
    }

    @Test
    void testPutVastDataBytes() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData();
        byteBuffer.put(bytes);
        assertArrayEquals(bytes, byteBuffer.toBytes());
    }

    @Test
    void testReadByteArrayData1() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData();
        byteBuffer.put(bytes);
        assertArrayEquals(bytes, byteBuffer.getLength(bytes.length));
    }

    @Test
    void testReadByteArrayData2() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData();
        byteBuffer.put(bytes);
        byte[] test = new byte[bytes.length];
        byteBuffer.get(test);
        assertArrayEquals(bytes, test);
    }


    private byte[] byteArrayData() {
        byte[] data = new byte[1000000];
        new TestSupportRandom().nextBytes(data);
        return data;
    }

    private short[] shortArrayData() {
        short[] array = new short[1000000];
        new TestSupportRandom().nextShorts(array);
        return array;
    }

    private int[] intArrayData() {
        int[] array = new int[1000000];
        new TestSupportRandom().nextInts(array);
        return array;
    }

    private long[] longArrayData() {
        long[] array = new long[1000000];
        new TestSupportRandom().nextLongs(array);
        return array;
    }

}

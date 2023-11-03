package org.gongxuanzhang.easybyte.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class SimpleBufferTest {


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testPutVastDataByteOneByOne(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData(length);
        for (byte datum : bytes) {
            byteBuffer.append(datum);
        }
        assertArrayEquals(bytes, byteBuffer.toBytes());
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testPutVastDataShortOneByOne(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData(length);
        for (byte datum : bytes) {
            byteBuffer.append(datum);
        }
        assertArrayEquals(bytes, byteBuffer.toBytes());
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testPutVastDataBytes(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData(length);
        byteBuffer.append(bytes);
        assertArrayEquals(bytes, byteBuffer.toBytes());
    }


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testReadByteArrayData1(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData(length);
        byteBuffer.append(bytes);
        assertArrayEquals(bytes, byteBuffer.getLength(bytes.length));
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testReadByteArrayData2(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byte[] bytes = byteArrayData(length);
        byteBuffer.append(bytes);
        byte[] test = new byte[bytes.length];
        byteBuffer.get(test);
        assertArrayEquals(bytes, test);
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testReadShortData(int length) {
        DynamicByteBuffer allocate = DynamicByteBuffer.allocate();
        short[] shorts = shortArrayData(length);
        for (short aShort : shorts) {
            allocate.appendShort(aShort);
        }
        short[] actual = new short[length];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = allocate.getShort();
        }
        assertArrayEquals(shorts, actual);
    }


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testReadIntData(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        int[] data = intArrayData(length);
        for (int datum : data) {
            byteBuffer.appendInt(datum);
        }
        int[] actual = new int[length];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = byteBuffer.getInt();
        }
        assertArrayEquals(data, actual);
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testReadLongData(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        long[] data = longArrayData(length);
        for (long datum : data) {
            byteBuffer.appendLong(datum);
        }
        long[] actual = new long[length];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = byteBuffer.getLong();
        }
        assertArrayEquals(data, actual);
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testReadDoubleData(int length) {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        double[] data = doubleArrayData(length);
        for (double datum : data) {
            byteBuffer.appendDouble(datum);
        }
        double[] actual = new double[length];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = byteBuffer.getDouble();
        }
        assertArrayEquals(data, actual, 0.001); // Specify a tolerance for double comparison
    }


    private byte[] byteArrayData(int length) {
        byte[] data = new byte[length];
        new TestSupportRandom().nextBytes(data);
        return data;
    }

    private short[] shortArrayData(int length) {
        short[] array = new short[length];
        new TestSupportRandom().nextShorts(array);
        return array;
    }

    private int[] intArrayData(int length) {
        int[] array = new int[length];
        new TestSupportRandom().nextInts(array);
        return array;
    }

    private long[] longArrayData(int length) {
        long[] array = new long[length];
        new TestSupportRandom().nextLongs(array);
        return array;
    }

    private double[] doubleArrayData(int length) {
        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = new TestSupportRandom().nextDouble();
        }
        return array;
    }


    private static Stream<Arguments> arrayLengthProvider() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(16),
                Arguments.of(1024),
                Arguments.of(1024 * 8),
                Arguments.of(1000000)
        );
    }

}

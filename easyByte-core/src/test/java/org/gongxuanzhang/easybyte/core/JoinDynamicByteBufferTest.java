package org.gongxuanzhang.easybyte.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class JoinDynamicByteBufferTest {

    @Test
    void testPutVastData() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.open();
        byte[] data = new byte[1000000];
        Arrays.fill(data, (byte) 2);
        for (byte datum : data) {
            byteBuffer.put(datum);
        }
        assertArrayEquals(data,byteBuffer.toBytes());
    }

}

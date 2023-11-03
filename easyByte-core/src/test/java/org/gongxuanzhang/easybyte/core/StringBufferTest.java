package org.gongxuanzhang.easybyte.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class StringBufferTest {


    @Test
    void testPutString() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        String uuid = UUID.randomUUID().toString();
        byteBuffer.appendString(uuid);
        String string = byteBuffer.getString();
        assertEquals(uuid,string);
    }


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testPutVastString(int length) {
        String s = new TestSupportRandom().nextString(length);
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byteBuffer.appendString(s);
        String string = byteBuffer.getString();
        assertEquals(s,string);
    }



    private static Stream<Arguments> arrayLengthProvider() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(2),
                Arguments.of(16),
                Arguments.of(1024),
                Arguments.of(1024 * 8),
                Arguments.of(1000000)
        );
    }



}

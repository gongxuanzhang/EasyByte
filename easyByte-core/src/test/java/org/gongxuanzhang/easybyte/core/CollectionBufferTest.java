package org.gongxuanzhang.easybyte.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CollectionBufferTest {


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testPutVastString(int length, int elementMaxLength) {
        List<String> strings = new ArrayList<>();
        TestSupportRandom testSupportRandom = new TestSupportRandom();
        for (int i = 0; i < length; i++) {
            strings.add(testSupportRandom.nextRandomLengthString(elementMaxLength));
        }
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        buffer.appendCollection(strings);
        List<String> collection = buffer.getCollection(String.class);
        assertArrayEquals(strings.toArray(new String[0]), collection.toArray(new String[0]));
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

package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.converter.read.StringReadConverter;
import org.gongxuanzhang.easybyte.core.converter.write.StringWriteConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class CollectionBufferTest {


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testAppendCollection(int length, int elementMaxLength) {
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


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testAppendCollectionSpecialConverter(int length, int elementMaxLength) {
        List<String> strings = new ArrayList<>();
        TestSupportRandom testSupportRandom = new TestSupportRandom();
        for (int i = 0; i < length; i++) {
            strings.add(testSupportRandom.nextRandomLengthString(elementMaxLength));
        }
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        Charset charset = StandardCharsets.ISO_8859_1;
        buffer.appendCollection(strings, StringWriteConverter.charset(charset));
        List<String> collection = buffer.getCollection(StringReadConverter.charset(charset));
        assertArrayEquals(strings.toArray(new String[0]), collection.toArray(new String[0]));
    }






    private static Stream<Arguments> arrayLengthProvider() {
        return Stream.of(
                Arguments.of(0, new TestSupportRandom().nextInt(32)),
                Arguments.of(2, new TestSupportRandom().nextInt(32)),
                Arguments.of(16, new TestSupportRandom().nextInt(32)),
                Arguments.of(1024, new TestSupportRandom().nextInt(32)),
                Arguments.of(1024 * 8, new TestSupportRandom().nextInt(32)),
                Arguments.of(1000000, new TestSupportRandom().nextInt(32))
        );
    }


}

package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.converter.read.StringReadConverter;
import org.gongxuanzhang.easybyte.core.converter.write.StringWriteConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class StringBufferTest {



    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testAppendString(int length) {
        String s = new TestSupportRandom().nextString(length);
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byteBuffer.appendString(s);
        String string = byteBuffer.getString();
        assertEquals(s,string);
    }

    @Test
    void testAppendEmpty(){
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byteBuffer.appendString("");
        assertNull(byteBuffer.getString());
    }

    @Test
    void testAppendOtherCharset(){
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        String base = "easy-byte";
        byteBuffer.appendString(base, StringWriteConverter.charset(StandardCharsets.ISO_8859_1));
        String string = byteBuffer.getString(StringReadConverter.charset(StandardCharsets.ISO_8859_1));
        assertEquals(string,base);
    }



    private static Stream<Arguments> arrayLengthProvider() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(2),
                Arguments.of(16),
                Arguments.of(32),
                Arguments.of(1024),
                Arguments.of(1024 * 8),
                Arguments.of(1000000)
        );
    }



}

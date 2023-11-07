package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.converter.read.DummyReadNameOnlyConverter;
import org.gongxuanzhang.easybyte.core.converter.read.StringReadConverter;
import org.gongxuanzhang.easybyte.core.converter.write.DummyWriteNameOnlyConverter;
import org.gongxuanzhang.easybyte.core.converter.write.StringWriteConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class MapBufferTest {


    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testPutMap(int length) {
        Map<String, String> map = new HashMap<>(length / 3 * 4 + 1);
        for (int i = 0; i < length; i++) {
            map.put(i + "", i + "");
        }
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        buffer.appendMap(map);
        Map<String, String> map1 = buffer.getMap(String.class, String.class);
        assertEquals(map, map1);
    }


    @Test
    void testSpecialConverter() {
        Map<String, Dummy> dummyMap = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String name = new TestSupportRandom().nextString(32);
            if (i % 5 == 0) {
                dummyMap.put(name, null);
            } else {
                dummyMap.put(name, new Dummy(i, name));
            }
        }
        dummyMap.put("", new Dummy());
        DynamicByteBuffer allocate = DynamicByteBuffer.allocate();
        Charset charset = StandardCharsets.ISO_8859_1;
        DummyWriteNameOnlyConverter nameOnly = new DummyWriteNameOnlyConverter();
        allocate.appendMap(dummyMap, nameOnly);
        allocate.appendMap(dummyMap, StringWriteConverter.charset(charset), nameOnly);
        Map<String, Dummy> map = allocate.getMap(String.class, new DummyReadNameOnlyConverter());
        map.forEach((name, dummy) -> {
            Dummy expect = dummyMap.get(name);
            if (expect == null) {
                assertNull(dummy);
            } else {
                assertEquals(expect.getName(), dummy.getName());
            }
        });
        map = allocate.getMap(StringReadConverter.charset(charset), new DummyReadNameOnlyConverter());
        map.forEach((name, dummy) -> {
            Dummy expect = dummyMap.get(name);
            if (expect == null) {
                assertNull(dummy);
            } else {
                assertEquals(expect.getName(), dummy.getName());
            }
        });
    }


    private static Stream<Arguments> arrayLengthProvider() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(2),
                Arguments.of(16),
                Arguments.of(1024),
                Arguments.of(1024),
                Arguments.of(1000000)
        );
    }


}

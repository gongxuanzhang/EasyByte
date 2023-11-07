package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.converter.read.DummyReadNameOnlyConverter;
import org.gongxuanzhang.easybyte.core.converter.write.DummyWriteNameOnlyConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        for (int i = 0; i < 10; i++) {
            String name = new TestSupportRandom().nextString(32);
            dummyMap.put(name, new Dummy(i, name));
        }
        DynamicByteBuffer allocate = DynamicByteBuffer.allocate();
        allocate.appendMap(dummyMap,new DummyWriteNameOnlyConverter());
        Map<String, Dummy> map = allocate.getMap(String.class, new DummyReadNameOnlyConverter());
        map.forEach((name,dummy)->{
            assertNull(dummy.getAge());
            assertEquals(name,dummy.getName());
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

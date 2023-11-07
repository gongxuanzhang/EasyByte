package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.converter.read.DummyReadConverter;
import org.gongxuanzhang.easybyte.core.environment.GlobalConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class ObjectBufferTest {


    @BeforeEach
    void init() {
        GlobalConfig config = GlobalConfig.getInstance();
        config.registerReadConverter(new DummyReadConverter());
    }

    @AfterEach
    void clearConfig() {
        GlobalConfig config = GlobalConfig.getInstance();
        config.clearRegister();
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testAppendObject(int length) {
        List<Dummy> list = getDummyList(length);
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        for (Dummy dummy : list) {
            buffer.appendObject(dummy);
        }
        List<Dummy> readList = new ArrayList<>();
        DummyReadConverter dummyReadConverter = new DummyReadConverter();
        for (int i = 0; i < list.size(); i++) {
            Dummy dummy = buffer.getObject(dummyReadConverter);
            readList.add(dummy);
        }
        assertArrayEquals(list.toArray(new Dummy[0]), readList.toArray(new Dummy[0]));
    }

    @ParameterizedTest
    @MethodSource("arrayLengthProvider")
    void testRegisterObject(int length) {
        List<Dummy> list = getDummyList(length);
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        for (Dummy dummy : list) {
            buffer.appendObject(dummy);
        }
        List<Dummy> readList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Dummy dummy = buffer.getObject(Dummy.class);
            readList.add(dummy);
        }
        assertArrayEquals(list.toArray(new Dummy[0]), readList.toArray(new Dummy[0]));
    }

    private static List<Dummy> getDummyList(int length) {
        List<Dummy> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Dummy dummy = new Dummy();
            if (i % 5 == 0) {
                dummy.setName(null);
                dummy.setAge(null);
            } else {
                dummy.setName(UUID.randomUUID().toString());
                dummy.setAge(i);
            }
            if (i % 7 == 0) {
                list.add(null);
            } else {
                list.add(dummy);
            }

        }
        return list;
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

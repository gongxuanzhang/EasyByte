package org.gongxuanzhang.easybyte.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TestSupportRandomTest {


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10, 100, 1000, 1000000})
    void testNextString(int length) {
        String s = new TestSupportRandom().nextString(length);
        assertEquals(s.length(), length);
    }

}

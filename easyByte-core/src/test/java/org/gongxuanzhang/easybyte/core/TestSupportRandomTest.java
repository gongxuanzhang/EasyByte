package org.gongxuanzhang.easybyte.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class TestSupportRandomTest {


    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 100, 1000, 1000000})
    void testNextString(int length) {
        String s = new TestSupportRandom().nextString(length);
        assertEquals(s.length(), length);
    }

    @Test
    void testNextString() {
        String s = new TestSupportRandom().nextString(0);
        assertNull(s);
    }

}

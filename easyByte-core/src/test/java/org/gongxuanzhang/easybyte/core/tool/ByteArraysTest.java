package org.gongxuanzhang.easybyte.core.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ByteArraysTest {

    @Test
    void testIntConversion() {
        int intValue = 12345;
        byte[] intBytes = ByteArrays.fromInt(intValue);
        int unwrappedInt = ByteArrays.wrapInt(intBytes);

        assertEquals(intValue, unwrappedInt);
    }

    @Test
    void testShortConversion() {
        short shortValue = 12345;
        byte[] shortBytes = ByteArrays.fromShort(shortValue);
        short unwrappedShort = ByteArrays.wrapShort(shortBytes);

        assertEquals(shortValue, unwrappedShort);
    }

    @Test
    void testLongConversion() {
        long longValue = 123456789012345L;
        byte[] longBytes = ByteArrays.fromLong(longValue);
        long unwrappedLong = ByteArrays.wrapLong(longBytes);

        assertEquals(longValue, unwrappedLong);
    }

    @Test
    void testIntConversionWithInvalidByteArray() {
        byte[] invalidBytes = {0x01, 0x02};

        assertThrows(IllegalArgumentException.class, () -> ByteArrays.wrapInt(invalidBytes));
    }

    @Test
    void testShortConversionWithInvalidByteArray() {
        byte[] invalidBytes = {0x01};

        assertThrows(IllegalArgumentException.class, () -> ByteArrays.wrapShort(invalidBytes));
    }

    @Test
    void testLongConversionWithInvalidByteArray() {
        byte[] invalidBytes = {0x01, 0x02, 0x03};

        assertThrows(IllegalArgumentException.class, () -> ByteArrays.wrapLong(invalidBytes));
    }


}

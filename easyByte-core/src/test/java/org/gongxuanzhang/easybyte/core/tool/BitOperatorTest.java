package org.gongxuanzhang.easybyte.core.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BitOperatorTest {

    @Test
    void testSetBitToOneByte() {
        byte byteValue = 0b1010101;
        int bitIndex = 3;

        byte resultAfterSettingOne = BitOperator.setBitToOne(byteValue, bitIndex);
        assertEquals(0b1011101, resultAfterSettingOne);
    }

    @Test
    void testSetBitToZeroByte() {
        byte byteValue = 0b1011101;
        int bitIndex = 3;

        byte resultAfterSettingZero = BitOperator.setBitToZero(byteValue, bitIndex);
        assertEquals(0b1010101, resultAfterSettingZero);
    }

    @Test
    void testSetBitToOneShort() {
        short shortValue = (short) 0b0;
        int bitIndex = 7;

        short resultAfterSettingOne = BitOperator.setBitToOne(shortValue, bitIndex);
        assertEquals(0b10000000, resultAfterSettingOne);
    }

    @Test
    void testSetBitToZeroShort() {
        short shortValue = (short) 0b1010111010101010;
        int bitIndex = 10;

        short resultAfterSettingZero = BitOperator.setBitToZero(shortValue, bitIndex);
        assertEquals((short) 0b1010101010101010, resultAfterSettingZero);
    }

    @Test
    void testSetBitToOneInt() {
        int intValue = 0b10101010101010100010101010101010;
        int bitIndex = 15;

        int resultAfterSettingOne = BitOperator.setBitToOne(intValue, bitIndex);
        assertEquals(0b10101010101010101010101010101010, resultAfterSettingOne);
    }

    @Test
    void testSetBitToZeroInt() {
        int intValue = 0b10101010101110101010101010101010;
        int bitIndex = 20;

        int resultAfterSettingZero = BitOperator.setBitToZero(intValue, bitIndex);
        assertEquals(0b10101010101010101010101010101010, resultAfterSettingZero);
    }

    @Test
    void testSetBitToOneLong() {
        long longValue = 0xffffffffL;
        int bitIndex = 31;

        long resultAfterSettingOne = BitOperator.setBitToOne(longValue, bitIndex);
        assertEquals(0xffffffffL, resultAfterSettingOne);
    }

    @Test
    void testSetBitToZeroLong() {
        long longValue = 0xffffffffffffffffL;
        int bitIndex = 31;

        long resultAfterSettingZero = BitOperator.setBitToZero(longValue, bitIndex);
        assertEquals(0xffffffff7fffffffL, resultAfterSettingZero);
    }

    @Test
    void testInvalidBitIndexThrowsException() {
        byte byteValue = 0b1010101;
        int invalidBitIndex = 8;

        assertThrows(IllegalArgumentException.class, () -> BitOperator.setBitToOne(byteValue, invalidBitIndex));
    }
}

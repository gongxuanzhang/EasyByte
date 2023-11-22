package org.gongxuanzhang.easybyte.core.tool;

/**
 * support create byte array from some way
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public class ByteArrays {


    /**
     * create a byte array from int, can use{@link ByteArrays#wrapInt(byte[])} reconvert
     *
     * @param value a int
     * @return byte array
     **/
    public static byte[] fromInt(int value) {
        byte[] bytes = new byte[Integer.BYTES];
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }

    /**
     * @param bytes a byte array,length must be 4
     * @return int
     **/
    public static int wrapInt(byte[] bytes) {
        if (bytes.length != Integer.BYTES) {
            throw new IllegalArgumentException("Byte array length must be 4 to convert to int");
        }
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                (bytes[3] & 0xFF);
    }

    /**
     * Create a byte array from short, can be converted back using wrapShort method
     *
     * @param value a short
     * @return byte array
     */
    public static byte[] fromShort(short value) {
        byte[] bytes = new byte[Short.BYTES];
        bytes[0] = (byte) (value >> 8);
        bytes[1] = (byte) value;
        return bytes;
    }

    /**
     * Converts a byte array to a short
     *
     * @param bytes a byte array, length must be 2
     * @return short
     */
    public static short wrapShort(byte[] bytes) {
        if (bytes.length != Short.BYTES) {
            throw new IllegalArgumentException("Byte array length must be 2 to convert to short");
        }
        return (short) ((bytes[0] & 0xFF) << 8 |
                (bytes[1] & 0xFF));
    }

    /**
     * Create a byte array from long, can be converted back using wrapLong method
     *
     * @param value a long
     * @return byte array
     */
    public static byte[] fromLong(long value) {
        byte[] bytes = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            bytes[i] = (byte) (value >> (8 * (Long.BYTES - 1 - i)));
        }
        return bytes;
    }

    /**
     * Converts a byte array to a long
     *
     * @param bytes a byte array, length must be 8
     * @return long
     */
    public static long wrapLong(byte[] bytes) {
        if (bytes.length != Long.BYTES) {
            throw new IllegalArgumentException("Byte array length must be 8 to convert to long");
        }
        return ((long) (bytes[0] & 0xFF) << 56 |
                (long) (bytes[1] & 0xFF) << 48 |
                (long) (bytes[2] & 0xFF) << 40 |
                (long) (bytes[3] & 0xFF) << 32 |
                (long) (bytes[4] & 0xFF) << 24 |
                (long) (bytes[5] & 0xFF) << 16 |
                (long) (bytes[6] & 0xFF) << 8 |
                (bytes[7] & 0xFF));
    }
}

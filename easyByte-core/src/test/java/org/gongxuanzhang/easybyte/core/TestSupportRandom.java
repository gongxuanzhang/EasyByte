package org.gongxuanzhang.easybyte.core;

import java.util.Random;
import java.util.UUID;


/**
 * @author gongxuanzhang
 **/
class TestSupportRandom extends Random {

    private static final int UUID_LENGTH = 32;

    public void nextShorts(short[] shorts) {
        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = (short) nextInt(Short.MAX_VALUE + 1);
        }
    }

    public void nextInts(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            ints[i] = nextInt();
        }
    }

    public void nextLongs(long[] longs) {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = nextLong();
        }
    }

    public void nextFloats(float[] floats) {
        for (int i = 0; i < floats.length; i++) {
            floats[i] = nextFloat();
        }
    }

    public void nextChars(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (nextInt(Character.MAX_VALUE + 1));
        }
    }

    public void nextBooleans(boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            booleans[i] = nextBoolean();
        }
    }

    public String nextString(int length) {
        if (length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int count = length / UUID_LENGTH;
        for (int i = 0; i < count; i++) {
            stringBuilder.append(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        stringBuilder.append(UUID.randomUUID().toString().replaceAll("-", ""), 0, length % UUID_LENGTH);
        return stringBuilder.toString();
    }

    public String nextRandomLengthString(int maxLength) {
        if (maxLength == 0) {
            return null;
        }
        return nextString(nextInt(maxLength));
    }


}

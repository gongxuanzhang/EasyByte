package org.gongxuanzhang.easybyte.core;

import java.util.Random;


/**
 *
 * @author gongxuanzhang
 **/
public class TestSupportRandom extends Random {

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

}

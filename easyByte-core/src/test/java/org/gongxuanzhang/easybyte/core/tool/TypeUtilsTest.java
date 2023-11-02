package org.gongxuanzhang.easybyte.core.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class TypeUtilsTest {


    @Test
    void testGeneric() {
        Assertions.assertSame(TypeUtils.getFirstGenericType(Son.class), Integer.class);
        Assertions.assertNull(TypeUtils.getFirstGenericType(NoneSon.class));
        Assertions.assertNull(TypeUtils.getFirstGenericType(None.class));
    }


    private static class Parent<B> {

    }

    private static class Son extends Parent<Integer> {

    }

    private static class NoneSon<B> extends Parent<B> {

    }

    private static class None {

    }
}

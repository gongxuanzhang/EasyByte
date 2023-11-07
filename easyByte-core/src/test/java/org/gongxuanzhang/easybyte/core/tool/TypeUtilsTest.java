package org.gongxuanzhang.easybyte.core.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


class TypeUtilsTest {


    @Test
    void testGeneric() {
        Type[] targetInterfaceGeneric = TypeUtils.getTargetInterfaceGeneric(Son.class, Parent.class);
        Assertions.assertNotNull(targetInterfaceGeneric);
        Type type = targetInterfaceGeneric[0];
        Assertions.assertSame(type, Integer.class);
        Type[] noneSon = TypeUtils.getTargetInterfaceGeneric(NoneSon.class, Parent.class);
        Assertions.assertEquals(1, noneSon.length);
        Assertions.assertTrue(TypeVariable.class.isAssignableFrom(noneSon[0].getClass()));
        Assertions.assertEquals(0, TypeUtils.getTargetInterfaceGeneric(None.class, Parent.class).length);
    }


    private interface Parent<B> {

    }

    private static class Son implements Parent<Integer> {

    }

    private static class NoneSon<B> implements Parent<B> {

    }

    private static class None {

    }
}

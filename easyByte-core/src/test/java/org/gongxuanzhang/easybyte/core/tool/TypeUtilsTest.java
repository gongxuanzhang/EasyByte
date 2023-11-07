package org.gongxuanzhang.easybyte.core.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TypeUtilsTest {


    @Test
    void testGeneric() {
        Type[] targetInterfaceGeneric = TypeUtils.getTargetInterfaceGeneric(Son.class, Parent.class);
        Assertions.assertNotNull(targetInterfaceGeneric);
        Type type = targetInterfaceGeneric[0];
        Assertions.assertSame(type, Integer.class);
        Type[] noneSon = TypeUtils.getTargetInterfaceGeneric(NoneSon.class, Parent.class);
        assertEquals(1, noneSon.length);
        Assertions.assertTrue(TypeVariable.class.isAssignableFrom(noneSon[0].getClass()));
        assertEquals(0, TypeUtils.getTargetInterfaceGeneric(None.class, Parent.class).length);
    }


    @Test
    void testNotSubclass(){
        Type[] targetInterfaceGeneric = TypeUtils.getTargetInterfaceGeneric(ArrayList.class, Map.class);
        assertEquals(0,targetInterfaceGeneric.length);
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

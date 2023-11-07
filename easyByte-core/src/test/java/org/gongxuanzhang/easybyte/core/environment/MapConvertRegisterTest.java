package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.exception.ConvertDuplicationException;
import org.gongxuanzhang.easybyte.core.exception.GenericNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


class MapConvertRegisterTest {


    @Test
    void testRegisterRead() {
        MapConvertRegister mapConvertRegister = new MapConvertRegister();
        assertNull(mapConvertRegister.findReadConverter(TestRead.class));
        TestRead testRead = new TestRead();
        mapConvertRegister.registerReadConverter(testRead);
        assertSame(testRead, mapConvertRegister.findReadConverter(TestRead.class));
    }

    static class TestRead implements ReadConverter<TestRead> {

        @Override
        public TestRead toObject(byte[] bytes, int length) {
            return null;
        }
    }

    @Test
    void testRegisterWrite() {
        MapConvertRegister mapConvertRegister = new MapConvertRegister();
        assertNull(mapConvertRegister.findWriteConverter(TestWrite.class));
        TestWrite testWrite = new TestWrite();
        mapConvertRegister.registerWriteConverter(testWrite);
        assertSame(testWrite, mapConvertRegister.findWriteConverter(TestWrite.class));
    }

    @Test
    void testNoGeneric() {
        MapConvertRegister mapConvertRegister = new MapConvertRegister();
        NoGeneric noGeneric = new NoGeneric();
        assertThrowsExactly(GenericNotFoundException.class, () -> mapConvertRegister.registerWriteConverter(noGeneric));

    }

    @SuppressWarnings("rawtypes")
    static class NoGeneric implements WriteConverter {

        @Override
        public byte[] toBytes(Object o) {
            return new byte[0];
        }
    }

    @Test
    void testDuplicationRegister() {
        MapConvertRegister mapConvertRegister = new MapConvertRegister();
        TestWrite testWrite = new TestWrite();
        mapConvertRegister.registerWriteConverter(testWrite);
        assertThrowsExactly(ConvertDuplicationException.class,
                () -> mapConvertRegister.registerWriteConverter(testWrite));
        TestRead testRead = new TestRead();
        mapConvertRegister.registerReadConverter(testRead);
        assertThrowsExactly(ConvertDuplicationException.class,
                () -> mapConvertRegister.registerReadConverter(testRead));
    }


    static class TestWrite implements WriteConverter<TestWrite> {


        @Override
        public byte[] toBytes(TestWrite testWrite) {
            return new byte[0];
        }
    }

}

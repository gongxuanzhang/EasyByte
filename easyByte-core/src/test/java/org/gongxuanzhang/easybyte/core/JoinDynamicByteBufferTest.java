package org.gongxuanzhang.easybyte.core;

import org.gongxuanzhang.easybyte.core.environment.DefaultEnvironment;
import org.gongxuanzhang.easybyte.core.environment.ObjectConfig;
import org.gongxuanzhang.easybyte.core.exception.ConverterNotFoundException;
import org.gongxuanzhang.easybyte.core.exception.GenericNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


class JoinDynamicByteBufferTest {


    @ParameterizedTest
    @MethodSource("byteArraySupply")
    void testWrap(byte[] bytes) {
        DynamicByteBuffer buffer = DynamicByteBuffer.wrap(bytes);
        assertArrayEquals(bytes, buffer.toBytes());
    }

    @Test
    void testConfig() {
        DynamicByteBuffer allocate = DynamicByteBuffer.allocate();
        assertNull(allocate.getProperty("a"));
        allocate.setProperty("a", "b");
        assertEquals("b", allocate.getProperty("a"));
        allocate.removeProperty("a");
        assertNull(allocate.getProperty("a"));
        assertEquals("b", allocate.getProperty("a", "b"));
        for (int i = 0; i < 10; i++) {
            allocate.setProperty(i + "", "b");
        }
        for (int i = 0; i < 10; i++) {
            assertEquals("b", allocate.getProperty(i + ""));
        }
        allocate.clearProperties();
        for (int i = 0; i < 10; i++) {
            assertNull(allocate.getProperty(i + ""));
        }
    }

    @Test
    void testConstructorWithConfig() {
        ObjectConfig objectConfig = new ObjectConfig();
        objectConfig.setProperty("a", "b");
        assertThrowsExactly(GenericNotFoundException.class, () -> {
            objectConfig.registerReadConverter((ReadConverter<JoinDynamicByteBufferTest>) (bytes, length) -> null);
        });
        objectConfig.registerReadConverter(new TestReadConverter());
        DynamicByteBuffer allocate = DynamicByteBuffer.allocate(objectConfig);
        assertEquals("b", allocate.getProperty("a"));
        assertEquals("b", allocate.getProperty("a", "c"));
        assertNotNull(allocate.findReadConverter(JoinDynamicByteBufferTest.class));

        DynamicByteBuffer buffer = DynamicByteBuffer.wrap(allocate.toBytes(), objectConfig);
        assertEquals("b", buffer.getProperty("a"));
        assertNotNull(buffer.findReadConverter(JoinDynamicByteBufferTest.class));

        buffer.clearRegister();
        assertThrowsExactly(ConverterNotFoundException.class,
                () -> buffer.findReadConverter(JoinDynamicByteBufferTest.class));
        allocate.removeProperty("a");
        assertEquals("c", allocate.getProperty("a", "c"));
    }

    @Test
    void testTraversalProperties() {
        DynamicByteBuffer allocate = DynamicByteBuffer.allocate();
        for (int i = 0; i < 10; i++) {
            allocate.setProperty(i + "", "");
        }
        Set<String> strings = allocate.keySet();
        Set<String> key = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            key.add(i + "");
        }
        for (DefaultEnvironment value : DefaultEnvironment.values()) {
            key.add(value.toString());
        }
        assertEquals(key, strings);
    }


    @Test
    void testRegisterConverter() {
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        //  read
        assertThrows(ConverterNotFoundException.class, () -> buffer.findReadConverter(JoinDynamicByteBufferTest.class));
        buffer.registerReadConverter(new TestReadConverter());
        assertNotNull(buffer.findReadConverter(JoinDynamicByteBufferTest.class));
        buffer.removeReadConverter(JoinDynamicByteBufferTest.class);
        assertThrows(ConverterNotFoundException.class, () -> buffer.findReadConverter(JoinDynamicByteBufferTest.class));
        //  write
        assertThrows(ConverterNotFoundException.class,
                () -> buffer.findWriteConverter(JoinDynamicByteBufferTest.class));
        buffer.registerWriteConverter(new TestWriteConverter());
        assertNotNull(buffer.findWriteConverter(JoinDynamicByteBufferTest.class));
        buffer.removeWriteConverter(JoinDynamicByteBufferTest.class);
        assertThrows(ConverterNotFoundException.class,
                () -> buffer.findWriteConverter(JoinDynamicByteBufferTest.class));


    }

    @Test
    void testNotConverter() {
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        Arbitrary arbitrary = new Arbitrary();
        assertThrows(ConverterNotFoundException.class, () -> buffer.appendObject(arbitrary));
    }

    static class TestReadConverter implements ReadConverter<JoinDynamicByteBufferTest> {

        @Override
        public JoinDynamicByteBufferTest toObject(byte[] bytes, int length) {
            return null;
        }
    }

    static class TestWriteConverter implements WriteConverter<JoinDynamicByteBufferTest> {

        @Override
        public byte[] toBytes(JoinDynamicByteBufferTest joinDynamicByteBufferTest) {
            return new byte[0];
        }
    }

    static class Arbitrary {

    }

    private static Stream<Arguments> byteArraySupply() {
        TestSupportRandom testSupportRandom = new TestSupportRandom();
        return Stream.of(0, 2, 16, 1024, 1024 * 8, 100000).map(byte[]::new).peek(testSupportRandom::nextBytes).map(Arguments::of);
    }


}

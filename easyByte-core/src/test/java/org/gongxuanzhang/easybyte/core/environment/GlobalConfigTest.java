package org.gongxuanzhang.easybyte.core.environment;

import org.junit.jupiter.api.Test;

import javax.swing.ComponentInputMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;


class GlobalConfigTest {


    @Test
     void testGetInstance() {
        GlobalConfig config1 = GlobalConfig.getInstance();
        GlobalConfig config2 = GlobalConfig.getInstance();

        assertSame(config1, config2);
    }

    @Test
    void testProperty(){
        GlobalConfig config = GlobalConfig.getInstance();
        config.setProperty("a","b");
        assertEquals("b",config.getProperty("a"));
        config.setProperty("a","a");
        assertEquals("a",config.getProperty("a"));
        assertNull(config.getProperty("b"));
    }

}

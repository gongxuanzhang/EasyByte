package org.gongxuanzhang.easybyte.core.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;


class GlobalConfigTest {


    @Test
     void testGetInstance() {
        GlobalConfig config1 = GlobalConfig.getInstance();
        GlobalConfig config2 = GlobalConfig.getInstance();

        assertSame(config1, config2);
    }

}

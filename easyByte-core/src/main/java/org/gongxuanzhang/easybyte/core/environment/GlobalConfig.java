package org.gongxuanzhang.easybyte.core.environment;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;

/**
 * Applies to global configuration
 * it is the last item in the configuration
 *
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class GlobalConfig extends BaseConfiguration {

    private GlobalConfig() {
        for (DefaultEnvironment item : DefaultEnvironment.values()) {
            this.setProperty(item.toString(), item.getDefaultValue());
        }
    }

    private static volatile GlobalConfig INSTANCE = null;

    public static GlobalConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (GlobalConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GlobalConfig();
                }
            }
        }
        return INSTANCE;
    }


}

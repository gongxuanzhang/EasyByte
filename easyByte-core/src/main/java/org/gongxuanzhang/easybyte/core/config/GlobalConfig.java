package org.gongxuanzhang.easybyte.core.config;

import org.gongxuanzhang.easybyte.core.EasyByteConfiguration;

/**
 * Applies to global configuration
 * it is the last item in the configuration
 *
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class GlobalConfig  {

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

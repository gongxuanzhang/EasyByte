package org.gongxuanzhang.easybyte.core.environment;


/**
 * an enum use for {@link EasyByteConfiguration} default properties
 * a item is pair of property
 * key is enum name
 * value is enum.value
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public enum DefaultEnvironment {

    /**
     * string convert charset
     **/
    STRING_CHARSET("utf-8");


    private final String value;

    DefaultEnvironment(String value) {
        this.value = value;
    }
}

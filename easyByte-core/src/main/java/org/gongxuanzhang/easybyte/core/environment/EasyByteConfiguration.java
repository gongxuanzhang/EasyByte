package org.gongxuanzhang.easybyte.core.environment;

/**
 * easy byte configuration
 * register for wrapper
 * and so on
 *
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public interface EasyByteConfiguration extends ConvertRegister {


    /**
     * set the property value and key
     *
     * @param key   key
     * @param value value
     **/
    void setProperty(String key, String value);

    /**
     * Return the property value
     *
     * @param key key
     * @return maybe null
     **/
    String getProperty(String key);

    /**
     * Return the property value, if not found, return the default value
     *
     * @param key          key
     * @param defaultValue default value
     * @return special value
     **/
    String getProperty(String key, String defaultValue);

}

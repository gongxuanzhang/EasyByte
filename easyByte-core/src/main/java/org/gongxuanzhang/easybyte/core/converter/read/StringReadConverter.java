package org.gongxuanzhang.easybyte.core.converter.read;

import org.gongxuanzhang.easybyte.core.ReadConverter;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class StringReadConverter implements ReadConverter<String> {

    private static final Map<Charset, StringReadConverter> INSTANCE_MAP = new ConcurrentHashMap<>();

    final Charset charset;

    private StringReadConverter(Charset charset) {
        this.charset = charset;
    }

    public static StringReadConverter charset(Charset charset) {
        return INSTANCE_MAP.computeIfAbsent(charset, StringReadConverter::new);
    }


    @Override
    public String toObject(byte[] bytes, int length) {
        if (length < 0) {
            return null;
        }
        if (bytes.length == 0) {
            return "";
        }
        return new String(bytes, charset);
    }
}

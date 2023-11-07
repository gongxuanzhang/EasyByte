package org.gongxuanzhang.easybyte.core.converter.write;

import org.gongxuanzhang.easybyte.core.WriteConverter;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class StringWriteConverter implements WriteConverter<String> {

    private static final Map<Charset, StringWriteConverter> INSTANCE_MAP = new ConcurrentHashMap<>();

    final Charset charset;

    private StringWriteConverter(Charset charset) {
        this.charset = charset;
    }

    public static StringWriteConverter charset(Charset charset) {
        return INSTANCE_MAP.computeIfAbsent(charset, StringWriteConverter::new);
    }


    @Override
    public byte[] toBytes(String s) {
        if (s == null) {
            return null;
        }
        if (s.isEmpty()) {
            return new byte[0];
        }
        return s.getBytes(this.charset);
    }
}

package org.gongxuanzhang.easybyte.core.converter.read;


import org.gongxuanzhang.easybyte.core.Dummy;
import org.gongxuanzhang.easybyte.core.ReadConverter;

import java.nio.charset.StandardCharsets;

public class DummyReadNameOnlyConverter implements ReadConverter<Dummy> {

    @Override
    public Dummy toObject(byte[] bytes) {
        StringReadConverter charset = StringReadConverter.charset(StandardCharsets.UTF_8);
        String name = charset.toObject(bytes);
        return new Dummy(name);
    }
}

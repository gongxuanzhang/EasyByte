package org.gongxuanzhang.easybyte.core.converter.read;


import org.gongxuanzhang.easybyte.core.Dummy;
import org.gongxuanzhang.easybyte.core.DynamicByteBuffer;
import org.gongxuanzhang.easybyte.core.ReadConverter;

import java.nio.charset.StandardCharsets;

public class DummyReadNameOnlyConverter implements ReadConverter<Dummy> {

    @Override
    public Dummy toObject(byte[] bytes, int length) {
        if (length < 0) {
            return null;
        }
        if (bytes.length == 0) {
            return new Dummy();
        }
        DynamicByteBuffer buffer = DynamicByteBuffer.wrap(bytes);
        String name = buffer.getString(StringReadConverter.charset(StandardCharsets.UTF_8));
        return new Dummy(name);
    }
}

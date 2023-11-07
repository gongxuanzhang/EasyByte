package org.gongxuanzhang.easybyte.core.converter.write;


import org.gongxuanzhang.easybyte.core.Dummy;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.converter.read.StringReadConverter;

import java.nio.charset.StandardCharsets;

public class DummyWriteNameOnlyConverter implements WriteConverter<Dummy> {


    @Override
    public byte[] toBytes(Dummy dummy) {
        StringWriteConverter converter = StringWriteConverter.charset(StandardCharsets.UTF_8);
        return converter.toBytes(dummy.getName());
    }
}

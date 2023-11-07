package org.gongxuanzhang.easybyte.core.converter.write;


import org.gongxuanzhang.easybyte.core.Dummy;
import org.gongxuanzhang.easybyte.core.DynamicByteBuffer;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.converter.read.StringReadConverter;

import java.nio.charset.StandardCharsets;

public class DummyWriteNameOnlyConverter implements WriteConverter<Dummy> {


    @Override
    public byte[] toBytes(Dummy dummy) {
        if(dummy == null){
           return null;
        }
        DynamicByteBuffer buffer = DynamicByteBuffer.allocate();
        buffer.appendString(dummy.getName(),StringWriteConverter.charset(StandardCharsets.UTF_8));
        return buffer.toBytes();
    }
}

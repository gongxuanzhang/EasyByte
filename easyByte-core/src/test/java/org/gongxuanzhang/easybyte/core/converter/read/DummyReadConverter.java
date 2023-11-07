package org.gongxuanzhang.easybyte.core.converter.read;

import org.gongxuanzhang.easybyte.core.Dummy;
import org.gongxuanzhang.easybyte.core.DynamicByteBuffer;
import org.gongxuanzhang.easybyte.core.ReadConverter;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class DummyReadConverter implements ReadConverter<Dummy> {

    @Override
    public Dummy toObject(byte[] bytes) {
        DynamicByteBuffer buffer = DynamicByteBuffer.wrap(bytes);
        int age = buffer.getInt();
        Dummy dummy = new Dummy();
        if (age == -1) {
            dummy.setAge(null);
        }else {
            dummy.setAge(age);
        }
        String name = buffer.getString();
        dummy.setName(name);
        return dummy;
    }
}

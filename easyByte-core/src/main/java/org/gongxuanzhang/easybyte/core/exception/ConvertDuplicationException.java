package org.gongxuanzhang.easybyte.core.exception;

import org.gongxuanzhang.easybyte.core.ConvertRegister;
import org.gongxuanzhang.easybyte.core.ReadConvert;
import org.gongxuanzhang.easybyte.core.WriteConvert;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class ConvertDuplicationException extends RuntimeException {


    public ConvertDuplicationException(ConvertRegister convertRegister, WriteConvert<?> convert) {
        super(convertRegister.getClass().getSimpleName() + "has been register " + convert.getClass().getSimpleName());
    }

    public ConvertDuplicationException(ConvertRegister convertRegister, ReadConvert<?> convert) {
        super(convertRegister.getClass().getSimpleName() + "has been register " + convert.getClass().getSimpleName());
    }
}

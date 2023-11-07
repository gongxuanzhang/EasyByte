package org.gongxuanzhang.easybyte.core.exception;

import org.gongxuanzhang.easybyte.core.ReadConverter;
import org.gongxuanzhang.easybyte.core.WriteConverter;
import org.gongxuanzhang.easybyte.core.environment.ConvertRegister;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class ConvertDuplicationException extends RuntimeException {


    public ConvertDuplicationException(ConvertRegister convertRegister, WriteConverter<?> convert) {
        super(convertRegister.getClass().getSimpleName() + " has been register " + convert.getClass().getSimpleName());
    }

    public ConvertDuplicationException(ConvertRegister convertRegister, ReadConverter<?> convert) {
        super(convertRegister.getClass().getSimpleName() + " has been register " + convert.getClass().getSimpleName());
    }
}

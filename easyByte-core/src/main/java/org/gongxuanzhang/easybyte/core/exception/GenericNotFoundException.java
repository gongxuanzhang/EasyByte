package org.gongxuanzhang.easybyte.core.exception;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
public class GenericNotFoundException extends RuntimeException {

    private static final String ANONYMOUS_NAME_ITEM = "$$";

    private static String maybeAnonymousMessage(Class<?> type) {
        if (!type.getName().contains(ANONYMOUS_NAME_ITEM)) {
            return "";
        }
        return "register converter not allow anonymous object";

    }

    public GenericNotFoundException(Class<?> type) {
        super(type.getSimpleName() + "generic does not exists " + maybeAnonymousMessage(type));
    }
}

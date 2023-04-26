package com.dumpBot.common;

public class Util {

    public static <T extends Enum<T>> T findEnumConstant(final Class<T> cls, final String name) {
        if (name == null) {
            return null;
        }
        for (T enumConstant : cls.getEnumConstants()) {
            if (enumConstant.name().equals(name)) {
                return enumConstant;
            }
        }
        return null;
    }

}

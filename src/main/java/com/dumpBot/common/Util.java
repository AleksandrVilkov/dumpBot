package com.dumpBot.common;

import com.dumpBot.model.callback.Callback;
import org.apache.commons.codec.digest.Md5Crypt;

public class Util {
    public static <T extends Enum<T>> T findEnumConstant(final Class<T> cls,
                                                         final String name) {
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

    public static String newMd5FromCalBack(Callback callback) {
        String hash = String.valueOf(callback.hashCode());
        return Md5Crypt.md5Crypt(hash.getBytes());
    }
}

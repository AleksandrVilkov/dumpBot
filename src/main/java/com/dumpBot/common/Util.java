package com.dumpBot.common;

import com.dumpBot.model.callback.Callback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.Md5Crypt;

public class Util {

    public static Callback readCallBack(String stringCallback) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(stringCallback, Callback.class);
    }

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

    public static String newMd5FromCalBack(Callback callback) {
        String hash = String.valueOf(callback.hashCode());
        return Md5Crypt.md5Crypt(hash.getBytes());
    }
}

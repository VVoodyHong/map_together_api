package com.mapto.api.common.util;

import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class CheckUtil {

    public static boolean isNullObject(Object ... objects) {
        for(Object object : objects) {
            if(Objects.isNull(object)) return true;
        }
        return  false;
    }

    public static boolean isEmptyString(String ... strings) {
        for(String string : strings) {
            if(string == null || string.isEmpty()) return true;
        }
        return  false;
    }
}

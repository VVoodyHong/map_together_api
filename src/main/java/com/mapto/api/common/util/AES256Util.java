package com.mapto.api.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Objects;

public class AES256Util {

    private static final String key  = "FUJjL9sqhEPLjH7+faI79L6FI5OAQU4r";

    public static Cipher cipher (Integer mode) {
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Key keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec iv = new IvParameterSpec(key.substring(0, 16).getBytes(StandardCharsets.UTF_8));
            c.init(mode, keySpec, iv);
            return c;
        }catch (GeneralSecurityException e) {
            return null;
        }
    }

    public static String encrypt(String str) {
        byte[] encrypted = new byte[0];
        try {
            encrypted = Objects.requireNonNull(cipher(Cipher.ENCRYPT_MODE))
                    .doFinal(str.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(encrypted));
    }

    public static String decrypt(String str) {
        try {
            byte[] byteStr = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            return new String(Objects.requireNonNull(cipher(Cipher.DECRYPT_MODE)).doFinal(byteStr));
        } catch (GeneralSecurityException e) {
            return str;
        }
    }
}
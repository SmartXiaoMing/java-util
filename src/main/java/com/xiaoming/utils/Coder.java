package com.xiaoming.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Coder {
    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    private static byte[] decodeHex(final char[] data) {
        final int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        final byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = Character.digit(data[j], 16) << 4;
            if (f == -1) {
                throw new RuntimeException("Illegal hexadecimal character " + data[j] + " at index " + j);
            }
            j++;
            int g = Character.digit(data[j], 16);
            if (g == -1) {
                throw new RuntimeException("Illegal hexadecimal character " + data[j] + " at index " + j);
            }
            f = f | g;
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    byte[] data;

    public Coder(String str) {
        data = str.getBytes(StandardCharsets.UTF_8);
    }

    public Coder(byte[] data) {
        this.data = data;
    }

    public static Coder from(byte[] data) {
        return new Coder(data);
    }

    public static Coder from(String str) {
        return new Coder(str);
    }

    public static Coder fromHex(String str) {
        return new Coder(decodeHex(str.toCharArray()));
    }

    public static Coder fromBase64(String str) {
        return new Coder(Base64.getDecoder().decode(str));
    }

    public Coder aesEncrypt(byte[] key, byte[] iv) {
        try {
            byte[] keyBytes = Arrays.copyOf(key, 16);
            byte[] ivBytes = Arrays.copyOf(iv, 16);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            data = cipher.doFinal(data);
            return this;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public Coder aesEncrypt(String key, String iv) {
        return aesEncrypt(key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
    }

    public Coder aesDecrypt(byte[] key, byte[] iv) {
        try {
            byte[] keyBytes = Arrays.copyOf(key, 16);
            byte[] ivBytes = Arrays.copyOf(iv, 16);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            data = cipher.doFinal(data);
            return this;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public Coder aesDecrypt(String key, String iv) {
        return aesDecrypt(key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
    }

    public Coder md5() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(data);
            data = messageDigest.digest();
            return this;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public Coder sha1() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(data);
            data = messageDigest.digest();
            return this;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public Coder sha256() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(data);
            data = messageDigest.digest();
            return this;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public byte[] asBytes() {
        return data;
    }

    public String asString() {
        return new String(data, StandardCharsets.UTF_8);
    }

    public String asHex() {
        return asHexLower();
    }

    public String asHexUpper() {
        return new String(encodeHex(data, DIGITS_UPPER));
    }

    public String asHexLower() {
        return new String(encodeHex(data, DIGITS_LOWER));
    }

    public String asBase64() {
        return Base64.getEncoder().encodeToString(data);
    }

    public String asBase64UrlSafe() {
        return Base64.getUrlEncoder().encodeToString(data);
    }
}


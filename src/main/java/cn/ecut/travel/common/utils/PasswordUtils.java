package cn.ecut.travel.common.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // 明文  --》加密算法+盐值(唯一)--》密文
    // 123456    MD5---->000000
    // 123456    MD5---->000000

    public static String encode(String pw) {

        return BCrypt.hashpw(pw, BCrypt.gensalt()); // MD5,HASH ,盐值加密
    }

    public static boolean checkPassword(String pw, String dbpw) {

        return BCrypt.checkpw(pw, dbpw); // MD5,HASH ,盐值加密
    }

}

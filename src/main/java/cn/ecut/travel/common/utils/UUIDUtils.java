package cn.ecut.travel.common.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
//        String s = UUID.randomUUID().toString();
//        System.out.println(s);
//        System.out.println(getUUid());
         String activateKey = "blog:user:activate:%s"; // %s占位符

        String format = String.format(activateKey, "123455");
        System.out.println(format);
    }
}

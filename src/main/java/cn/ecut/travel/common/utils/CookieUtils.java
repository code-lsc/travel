package cn.ecut.travel.common.utils;


import cn.ecut.travel.common.constants.UserConstants;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

    /**
     * 根据name获取Cookie中的值
     * @param request
     * @return
     */
    public static String getValue(HttpServletRequest request) {
        if (request == null || ObjectUtils.isEmpty(UserConstants.TOKEN_COOKIE_KEY)) {

        }

        // 1.先从request对象获取Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if(UserConstants.TOKEN_COOKIE_KEY.equals(cookies[i].getName())){
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }
}

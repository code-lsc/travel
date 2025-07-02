package cn.ecut.travel.interceptor;

import cn.ecut.travel.common.constants.UserConstants;
import cn.ecut.travel.common.help.UserHelp;
import cn.ecut.travel.common.utils.CookieUtils;
import cn.ecut.travel.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserHelp userHelp;


    //controller前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getValue(request);
        if (!ObjectUtils.isEmpty(token)){
            UserToken userToken= (UserToken) redisTemplate.opsForValue().get(
                    String.format(UserConstants.USER_LOGIN_TOKEN, token));
            //userToken不能为空且token没有过期
            if (userToken != null){
                userHelp.set(userToken);

            }else {
                log.debug("token不存在或过期");
            }
        }

        return true;
    }

    //最后执行,一般用来销毁资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userHelp.remove();
    }
}

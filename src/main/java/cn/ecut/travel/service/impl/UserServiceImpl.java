package cn.ecut.travel.service.impl;

import cn.ecut.travel.common.constants.UserConstants;
import cn.ecut.travel.common.help.UserHelp;
import cn.ecut.travel.common.utils.CodeUtils;
import cn.ecut.travel.common.utils.EmailUtils;
import cn.ecut.travel.common.utils.PasswordUtils;
import cn.ecut.travel.common.utils.UUIDUtils;
import cn.ecut.travel.dao.UserDao;
import cn.ecut.travel.dto.Email;
import cn.ecut.travel.entity.UserEntity;
import cn.ecut.travel.service.UserService;
import cn.ecut.travel.vo.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private UserHelp userHelp;

    @Override
    public Boolean existsEmail(String email) {
        if (userDao.selectCountByEmail(email)>0)
            return true;
        return false;
    }

    @Value("${travel.host}")
    private String host;
    @Override
    public Boolean insertUser(UserEntity userEntity) {
        userEntity.setCreateTime(new Date());
        userEntity.setPassword(PasswordUtils.encode(userEntity.getPassword()));
        userEntity.setStatus(1);
        int randomNumber = (int) (Math.random() * 6) + 1;
        String headUrl=host+"icon/head"+randomNumber+".jpg";
        userEntity.setHeadUrl(headUrl);
        if (userDao.insertUser(userEntity)>0)
            return true;
        return false;
    }

    @Override
    public Boolean sendEmail(String user) {
        String code = CodeUtils.generateRandomNumberCode(6);
        System.out.println("验证码："+code);
        System.out.println("user:"+user);
        Email email = new Email("旅游网注册", "验证码：<span style='color: #2da1e7'>" + code + "</span><br>5分钟后失效", user);
        String key=String.format(UserConstants.IDENTIFY_CODE,user);
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);
        emailUtils.sendEmail(email);
        return true;
    }

    @Override
    public UserToken login(String email,String password) {
        UserEntity user = userDao.selectUserByEmail(email);
        UserToken userToken = new UserToken();
        if (user==null){
            return null;
        }
        if (!PasswordUtils.checkPassword(password,user.getPassword())){
            userToken.setToken("password");
            return userToken;
        }

        if (user.getStatus() == 0){
            userToken.setToken("status");
            return userToken;
        }
        userToken.setId(user.getId());
        userToken.setName(user.getName());
        userToken.setSex(user.getSex());
        userToken.setAge(user.getAge());
        userToken.setEmail(user.getEmail());
        userToken.setPhone(user.getPhone());
        userToken.setAddress(user.getAddress());
        userToken.setHeadUrl(user.getHeadUrl());
        userToken.setToken(UUIDUtils.getUUid());
        userToken.setType(user.getType());
        String key = String.format(UserConstants.USER_LOGIN_TOKEN,userToken.getToken());
        redisTemplate.opsForValue().set(key,
                userToken,
                7,
                TimeUnit.DAYS);

        return userToken;
    }

    @Override
    public void logout(String token) {
        String key = String.format(UserConstants.USER_LOGIN_TOKEN,token);
        redisTemplate.delete(key);
    }

    @Override
    public Boolean updateUserInfo(UserToken userTokens) {
        boolean b = userDao.updateUserInfo(userTokens) > 0 ? true : false;
        String key = String.format(UserConstants.USER_LOGIN_TOKEN,userHelp.get().getToken());
        Long expireTimeInSeconds = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (b){
            UserToken userToken = userHelp.get();
            userToken.setName(userTokens.getName());
            userToken.setSex(userTokens.getSex());
            userToken.setAge(userTokens.getAge());
            userToken.setPhone(userTokens.getPhone());
            userToken.setAddress(userTokens.getAddress());
            redisTemplate.opsForValue().set(key,
                    userToken,
                    expireTimeInSeconds,
                    TimeUnit.SECONDS);
        }
        return b;
    }

    @Override
    public Boolean updateHeadUrl(String headUrl,Integer id) {
        boolean b =userDao.updateHeadUrl(headUrl,id)>0?true:false;
        String key = String.format(UserConstants.USER_LOGIN_TOKEN,userHelp.get().getToken());
        Long expireTimeInSeconds = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (b){
            UserToken userToken = userHelp.get();
            userToken.setHeadUrl(headUrl);
            redisTemplate.opsForValue().set(key,
                    userToken,
                    expireTimeInSeconds,
                    TimeUnit.SECONDS);
        }
        return b;

    }

    @Override
    public String selectPassword(Integer id) {
        return userDao.selectPassword(id);
    }

    @Override
    public Boolean updatePassword(String password, Integer id) {
        return userDao.updatePassword(password,id)>0?true:false;
    }

    @Override
    public Boolean updateUserByAdmin(Integer type, Integer status, Integer id) {
        return userDao.updateUserByAdmin(type, status, id)>0?true:false;
    }

    @Override
    public List<UserEntity> selectAllUser() {
        return userDao.selectAllUser();
    }


}

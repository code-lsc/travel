package cn.ecut.travel.controller;

import cn.ecut.travel.common.constants.UserConstants;
import cn.ecut.travel.common.exception.TravelException;
import cn.ecut.travel.common.help.UserHelp;
import cn.ecut.travel.common.utils.PasswordUtils;
import cn.ecut.travel.common.utils.UUIDUtils;
import cn.ecut.travel.entity.AddressEntity;
import cn.ecut.travel.entity.UserEntity;
import cn.ecut.travel.service.AddressService;
import cn.ecut.travel.service.UserService;
import cn.ecut.travel.vo.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserHelp userHelp;

    /**
     * 判断邮箱是否存在
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public Boolean existsEmail(@PathVariable String email){
       return userService.existsEmail(email);
    }

    @GetMapping("/email/send/{user}")
    public Boolean sendEmail(@PathVariable String user){
        if (userService.sendEmail(user))
            return true;
        return false;
    }

    @PostMapping("/email/check")
    public Boolean checkCode(@RequestBody Map<String,Object> resultMap){
        String code = (String) resultMap.get("code");
        String email= (String) resultMap.get("email");
        String key = String.format(UserConstants.IDENTIFY_CODE, email);
        String value = (String) redisTemplate.opsForValue().get(key);
        if (code.equals(value))
            return true;
        return false;
    }

    @PostMapping("/add")
    public Boolean register(@RequestBody Map<String,Object> resultMap){
        String email = (String) resultMap.get("email");
        String password = (String) resultMap.get("password");
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);
        return userService.insertUser(user);
    }

    @PostMapping("/login")
    public UserToken login(@RequestBody Map<String,Object> resultMap, HttpServletResponse response){
        String email = (String) resultMap.get("email");
        String password = (String) resultMap.get("password");
        UserToken userToken = userService.login(email, password);
        if (userToken==null)
            return null;
        Cookie cookie = new Cookie(UserConstants.TOKEN_COOKIE_KEY, userToken.getToken());
        cookie.setMaxAge(7*24*60*60);//7天
        cookie.setPath("/");
        response.addCookie(cookie);
        return userToken;
    }
    @GetMapping("/info")
    public UserToken returnUserInfo(){
        if (userHelp.get()==null)
            throw new TravelException("用户未登录");
        return userHelp.get();

    }

    @GetMapping("/logout")
    public Boolean logout(@CookieValue(name = UserConstants.TOKEN_COOKIE_KEY, defaultValue = "")
                                      String token, HttpServletResponse response) {
        userService.logout(token);
        Cookie cookie = new Cookie(UserConstants.TOKEN_COOKIE_KEY, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    @PostMapping("/update")
    public Boolean updateUserInfo(@RequestBody UserToken userToken){
        if (userHelp.get()==null || userToken.getId()!=userHelp.get().getId())
            throw new TravelException("用户未登录");
        AddressEntity addressEntity = addressService.selectAddress(userToken.getAddress());
        userToken.setAddress(addressEntity);
        return userService.updateUserInfo(userToken);
    }

    @PostMapping("/headUrl")
    public Boolean updateHeadUrl(@RequestParam("file") MultipartFile file){
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        if (file.isEmpty()) {
            throw new TravelException("文件不能为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String[] split = fileName.split("\\.");
        fileName= UUIDUtils.getUUid()+"."+split[1];
        try {
            // 确保上传目录存在
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 保存文件
            String savePath = Paths.get(uploadDir, fileName).toString();
            file.transferTo(new File(savePath));
            return userService.updateHeadUrl("/uploads/"+fileName,userHelp.get().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/password")
    public String updatePassword(@RequestBody Map<String,Object> resultMap,HttpServletResponse response){
        String oldPassword = (String) resultMap.get("oldPassword");
        String newPassword = (String) resultMap.get("newPassword");
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        String pwd = userService.selectPassword(userHelp.get().getId());
        if (PasswordUtils.checkPassword(oldPassword,pwd)){
            logout(userHelp.get().getToken(),response);
            return userService.updatePassword(PasswordUtils.encode(newPassword),userHelp.get().getId())?"ok":"no";
        }else {
            return "error";
        }

    }


}

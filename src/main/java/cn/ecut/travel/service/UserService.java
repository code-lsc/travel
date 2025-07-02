package cn.ecut.travel.service;
import cn.ecut.travel.entity.UserEntity;
import cn.ecut.travel.vo.UserToken;

import java.util.List;


public interface UserService {

   Boolean existsEmail(String email);

   Boolean insertUser(UserEntity userEntity);

   Boolean sendEmail(String user);

   UserToken login(String email, String password);

   void logout(String token);

   Boolean updateUserInfo(UserToken userToken);

   Boolean updateHeadUrl(String headUrl,Integer id);

   String selectPassword(Integer id);

   Boolean updatePassword(String password,Integer id);

   Boolean updateUserByAdmin(Integer type, Integer status, Integer id);

   List<UserEntity> selectAllUser();

}

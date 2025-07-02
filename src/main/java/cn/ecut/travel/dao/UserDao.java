package cn.ecut.travel.dao;

import cn.ecut.travel.entity.UserEntity;
import cn.ecut.travel.vo.UserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    /**
     * 查询指定邮箱的数量
     * @param email
     * @return
     */
    Integer selectCountByEmail(@Param("email") String email);

    /**
     * 插入用户
     * @return
     */
    Integer insertUser(UserEntity userEntity);

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    UserEntity selectUserByEmail(@Param("email") String email);

    /**
     * 修改用户信息
     *
     *
     * @return
     */
    Integer updateUserInfo(UserToken userToken);

    /**
     * 修改用户头像
     * @param headUrl
     * @return
     */
    Integer updateHeadUrl(@Param("headUrl") String headUrl,@Param("id") Integer id);

    /**
     * 根据用户id查密码
     * @param id
     * @return
     */
    String selectPassword(@Param("id") Integer id);

    /**
     * 修改密码
     * @param password
     * @param id
     * @return
     */
    Integer updatePassword(@Param("password") String password,@Param("id") Integer id);

    Integer updateUserByAdmin(@Param("type") Integer type,@Param("status") Integer status,@Param("id") Integer id);

    List<UserEntity> selectAllUser();
}

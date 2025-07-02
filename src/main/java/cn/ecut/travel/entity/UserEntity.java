package cn.ecut.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class UserEntity {
   private Integer id;
   private String name;
   private Integer sex;
   private Integer age;
   private String email;
   private Integer type;
   private String password;
   private String phone;
   private AddressEntity address;
   private Integer status;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private Date createTime;
   private String headUrl;


}


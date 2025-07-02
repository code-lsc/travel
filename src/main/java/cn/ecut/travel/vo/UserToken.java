package cn.ecut.travel.vo;

import cn.ecut.travel.entity.AddressEntity;
import lombok.Data;
import java.io.Serializable;
@Data
public class UserToken implements Serializable {
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
    private String email;
    private String phone;
    private Integer type;
    private AddressEntity address;
    private String headUrl;
    private String token;
}

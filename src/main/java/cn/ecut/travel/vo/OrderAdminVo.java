package cn.ecut.travel.vo;

import cn.ecut.travel.entity.SceneryEntity;
import cn.ecut.travel.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderAdminVo {
    private Integer id;
    private SceneryEntity scenery;
    private UserEntity user;
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date useDate;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}

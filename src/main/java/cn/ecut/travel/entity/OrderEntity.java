package cn.ecut.travel.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderEntity {
    private Integer id;
    private Integer sceneryId;
    private Integer uid;
    private BigDecimal price;
    private Date useDate;
    private Integer status;
    private Date createTime;
}

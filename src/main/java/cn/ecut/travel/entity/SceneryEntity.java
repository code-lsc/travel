package cn.ecut.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SceneryEntity {
    private Integer id;
    private String name;
    private Integer grade;//'0,1,2,3,,无等级，3A,4A,5A'
    private AddressEntity address;
    private String profile;
    private Integer status;
    private BigDecimal ticketPrice;
    private Integer ticketNumber;
    private String attention;
    private Double score;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String imageUrl;
}

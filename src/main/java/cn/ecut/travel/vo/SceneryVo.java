package cn.ecut.travel.vo;

import cn.ecut.travel.entity.AddressEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SceneryVo {
    private Integer id;
    private String name;
    private Integer grade;
    private AddressEntity address;
    private String profile;
    private Double score;
    private Integer status;
    private String imageUrl;
    private BigDecimal ticketPrice;
}

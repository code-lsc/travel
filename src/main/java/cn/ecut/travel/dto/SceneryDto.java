package cn.ecut.travel.dto;

import cn.ecut.travel.entity.AddressEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SceneryDto {
    private Integer id;
    private String name;
    private Integer grade;
    private AddressEntity address;
    private String profile;
    private BigDecimal ticketPrice;
    private String attention;
    private Integer status;
    private String description;
    private String imageUrl;
    private List<Integer> topic;
    private List<String> urls;
}

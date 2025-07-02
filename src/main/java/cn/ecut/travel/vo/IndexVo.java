package cn.ecut.travel.vo;

import cn.ecut.travel.entity.AddressEntity;
import lombok.Data;

@Data
public class IndexVo {
    private Integer id;
    private String name;
    private Integer grade;
    private AddressEntity address;
    private String profile;
    private Double score;
    private String imageUrl;
}

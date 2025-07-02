package cn.ecut.travel.vo;

import cn.ecut.travel.entity.AddressEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SceneryDetailVo {
    private Integer id;//风景id
    private String name;//风景名
    private Integer grade;
    private AddressEntity address;
    private String profile;
    private BigDecimal ticketPrice;
    private String attention;
    private Double score;
    private String description;
    private List<String> urls;//风景的多张图片
    private List<SceneryCommentVo> comment;//评论

}

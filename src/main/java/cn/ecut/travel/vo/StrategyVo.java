package cn.ecut.travel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StrategyVo {
    private Integer id;//攻略id
    private String title;
    private String imageUrl;
    private Long likeNumber;
    private Boolean isLike;
    private Integer uid;//用户id
    private String name;//用户名
    private String headUrl;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;


}

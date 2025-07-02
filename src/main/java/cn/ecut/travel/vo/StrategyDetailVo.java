package cn.ecut.travel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class StrategyDetailVo {
    private Integer id;
    private Integer uid;
    private String name;
    private String headUrl;
    private String title;
    private String content;
    private String imageUrl;
    private Long likeNumber;
    private Boolean isLike;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private List<CommentVo> comment;


}

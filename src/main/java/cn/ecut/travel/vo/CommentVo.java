package cn.ecut.travel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class CommentVo {
    private Integer id;
    private Integer uid;
    private String name;
    private String headUrl;
    private Integer type;//回复类型，0是回复帖子，1是回复评论
    private Integer entityId;//回复的目标的id，帖子或评论的id
    private String targetName;//回复目标的id，发帖人或发评论人的id
    private Long likeNumber;
    private Boolean isLike;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private List<CommentVo> reply;



}

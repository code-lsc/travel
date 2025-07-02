package cn.ecut.travel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class SceneryCommentVo {
    private Integer id;//风景评论的主键id
    private Integer uid;//发评论的用户id
    private String name;//用户名
    private String headUrl;//用户头像
    private Integer score;//评分
    private String content;//评论内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//评论创建时间

}

package cn.ecut.travel.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CommentEntity {
    private Integer id;
    private Integer uid;
    private Integer type;
    private Integer entityId;
    private Integer targetId;
    private String content;
    private Integer status;
    private Date createTime;
}

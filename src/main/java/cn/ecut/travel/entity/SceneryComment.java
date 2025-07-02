package cn.ecut.travel.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SceneryComment {
    private Integer id;
    private Integer orderId;
    private String content;
    private Integer score;
    private Date createTime;
    private Integer status;
}

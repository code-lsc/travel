package cn.ecut.travel.entity;

import lombok.Data;

import java.util.Date;
@Data
public class StrategyEntity {
    private Integer id;
    private String title;
    private String content;
    private Integer status;
    private Integer uid;
    private String imageUrl;
    private Date createTime;
}

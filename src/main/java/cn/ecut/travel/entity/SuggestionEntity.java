package cn.ecut.travel.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SuggestionEntity {
    private Integer id;
    private Integer uid;
    private String title;
    private Integer type;
    private String options;
    private String content;
    private String name;
    private String phone;
    private Integer status;
    private Date createTime;
}

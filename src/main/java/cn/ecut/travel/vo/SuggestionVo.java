package cn.ecut.travel.vo;

import cn.ecut.travel.entity.ReplyEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SuggestionVo {
    private Integer id;
    private Integer uid;
    private String title;
    private Integer type;
    private String options;
    private String content;
    private String name;
    private String phone;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private ReplyEntity reply;


}

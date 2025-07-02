package cn.ecut.travel.dao;

import cn.ecut.travel.entity.ReplyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReplyDao {

    Integer insertReply(ReplyEntity replyEntity);

    void updateReplyStatus(@Param("id") Integer id);
}

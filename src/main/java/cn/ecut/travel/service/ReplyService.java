package cn.ecut.travel.service;

import cn.ecut.travel.entity.ReplyEntity;

public interface ReplyService {

    Boolean insertReply(ReplyEntity replyEntity);

    void updateReplyStatus(Integer id);
}

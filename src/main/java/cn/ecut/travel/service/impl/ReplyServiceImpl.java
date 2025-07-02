package cn.ecut.travel.service.impl;

import cn.ecut.travel.dao.ReplyDao;
import cn.ecut.travel.entity.ReplyEntity;
import cn.ecut.travel.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Override
    public Boolean insertReply(ReplyEntity replyEntity) {
        return replyDao.insertReply(replyEntity)>0?true:false;
    }

    @Override
    public void updateReplyStatus(Integer id) {
        replyDao.updateReplyStatus(id);
    }
}

package cn.ecut.travel.service.impl;

import cn.ecut.travel.dao.CommentDao;
import cn.ecut.travel.entity.CommentEntity;
import cn.ecut.travel.service.CommentService;
import cn.ecut.travel.vo.CommentVo;
import cn.ecut.travel.vo.SceneryCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<SceneryCommentVo> selectCommentBySceneryId(Integer sceneryId) {
        return commentDao.selectCommentBySceneryId(sceneryId);
    }

    @Override
    public List<CommentVo> selectCommentByEntityId(Integer type, Integer entityId) {
        return commentDao.selectCommentByEntityId(type, entityId);
    }

    @Override
    public Boolean addComment(CommentEntity commentEntity) {
        return commentDao.insertComment(commentEntity)>0?true:false;
    }

    @Override
    public Boolean deleteComment(Integer commentId) {
        return commentDao.deleteCommentById(commentId)>0?true:false;
    }
}

package cn.ecut.travel.service;

import cn.ecut.travel.entity.CommentEntity;
import cn.ecut.travel.vo.CommentVo;
import cn.ecut.travel.vo.SceneryCommentVo;

import java.util.List;

public interface CommentService {

    List<SceneryCommentVo> selectCommentBySceneryId(Integer sceneryId);

    List<CommentVo> selectCommentByEntityId(Integer type,Integer entityId);

    Boolean addComment(CommentEntity commentEntity);

    Boolean deleteComment(Integer commentId);
}

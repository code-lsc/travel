package cn.ecut.travel.dao;

import cn.ecut.travel.entity.CommentEntity;
import cn.ecut.travel.vo.CommentVo;
import cn.ecut.travel.vo.SceneryCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    /**
     * 根据风景id查询评论
     * @param sceneryId
     * @return
     */
    List<SceneryCommentVo> selectCommentBySceneryId(@Param("sceneryId") Integer sceneryId);

    /**
     * 根据实体id查询评论信息
     * @param type
     * @param entityId
     * @return
     */
    List<CommentVo> selectCommentByEntityId(@Param("type") Integer type,@Param("entityId") Integer entityId);

    /**
     * 插入评论
     * @param commentEntity
     * @return
     */
    Integer insertComment(CommentEntity commentEntity);

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    Integer deleteCommentById(@Param("commentId") Integer commentId);
}

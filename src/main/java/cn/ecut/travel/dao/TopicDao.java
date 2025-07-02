package cn.ecut.travel.dao;

import cn.ecut.travel.entity.TopicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TopicDao {

    List<TopicEntity> selectAllTopic();

    Integer insertTopic(@Param("sceneryId") Integer sceneryId,@Param("topicId") List<Integer> topicId);

    List<Integer> selectTopicBySceneryId(@Param("sceneryId") Integer sceneryId);

    Integer deleteTopicAndSceneryBySceneryId(@Param("sceneryId") Integer sceneryId);
}

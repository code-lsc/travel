package cn.ecut.travel.service;

import cn.ecut.travel.entity.TopicEntity;

import java.util.List;

public interface TopicService {

    List<TopicEntity> selectAllTopic();

    Boolean insertTopic(Integer sceneryId, List<Integer> topicId);

    List<Integer> selectTopicBySceneryId(Integer sceneryId);

    Boolean deleteTopicAndSceneryBySceneryId(Integer sceneryId);

}

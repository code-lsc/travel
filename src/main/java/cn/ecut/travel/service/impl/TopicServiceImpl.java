package cn.ecut.travel.service.impl;

import cn.ecut.travel.dao.TopicDao;
import cn.ecut.travel.entity.TopicEntity;
import cn.ecut.travel.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Override
    public List<TopicEntity> selectAllTopic() {
        return topicDao.selectAllTopic();
    }

    @Override
    public Boolean insertTopic(Integer sceneryId, List<Integer> topicId) {
        return topicDao.insertTopic(sceneryId, topicId)>0?true:false;
    }

    @Override
    public List<Integer> selectTopicBySceneryId(Integer sceneryId) {
        return topicDao.selectTopicBySceneryId(sceneryId);
    }

    @Override
    public Boolean deleteTopicAndSceneryBySceneryId(Integer sceneryId) {
        return topicDao.deleteTopicAndSceneryBySceneryId(sceneryId)>0?true:false;
    }

}

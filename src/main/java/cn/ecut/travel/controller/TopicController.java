package cn.ecut.travel.controller;

import cn.ecut.travel.entity.TopicEntity;
import cn.ecut.travel.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/all")
    public List<TopicEntity> allTopic(){
        return topicService.selectAllTopic();
    }
}

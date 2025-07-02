package cn.ecut.travel.controller;

import cn.ecut.travel.dto.SceneryDto;
import cn.ecut.travel.entity.AddressEntity;
import cn.ecut.travel.entity.SceneryEntity;
import cn.ecut.travel.entity.UserEntity;
import cn.ecut.travel.service.AddressService;
import cn.ecut.travel.service.SceneryService;
import cn.ecut.travel.service.TopicService;
import cn.ecut.travel.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private SceneryService sceneryService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/hotCity/get")
    public List<String> getHotCity(){
        return addressService.selectHotCity();
    }

    @PostMapping("/hotCity/update")
    public void updateHotCity(@RequestBody List<String> address){
        addressService.updateHotCity(address);
    }

    @GetMapping("/user/update")
    public Boolean updateUserInfo(@RequestParam("type") Integer type,
                                  @RequestParam("status") Integer status,
                                  @RequestParam("id") Integer id){
        return userService.updateUserByAdmin(type, status, id);
    }

    @GetMapping("/user/all")
    public List<UserEntity> selectAllUser(){
        return userService.selectAllUser();
    }

    @PostMapping("/scenery/add")
    public Boolean addScenery(@RequestBody SceneryDto sceneryDto){
        AddressEntity addressEntity = addressService.selectAddress(sceneryDto.getAddress());
        SceneryEntity scenery = new SceneryEntity();
        scenery.setName(sceneryDto.getName());
        scenery.setAddress(addressEntity);
        scenery.setGrade(sceneryDto.getGrade());
        scenery.setProfile(sceneryDto.getProfile());
        scenery.setTicketPrice(sceneryDto.getTicketPrice());
        scenery.setAttention(sceneryDto.getAttention());
        scenery.setDescription(sceneryDto.getDescription());
        scenery.setImageUrl(sceneryDto.getImageUrl());
        scenery.setCreateTime(new Date());
        scenery.setStatus(sceneryDto.getStatus());
        sceneryService.insertScenery(scenery);
        Integer sceneryId = sceneryService.selectSceneryIdByProfile(sceneryDto.getProfile());
        topicService.insertTopic(sceneryId, sceneryDto.getTopic());
       return sceneryService.insertImgs(sceneryId, sceneryDto.getUrls());
    }

    @GetMapping("/scenery/all")
    public List<SceneryEntity> getAllScenery(){
        return sceneryService.selectAllScenery();
    }

    @GetMapping("/scenery/search")
    public SceneryDto selectSceneryById(@Param("id") Integer id){
        SceneryDto sceneryDto = sceneryService.selectSceneryById(id);
        List<String> imgs = sceneryService.selectImgsBySceneryId(id);
        List<Integer> topic = topicService.selectTopicBySceneryId(id);
        sceneryDto.setUrls(imgs);
        sceneryDto.setTopic(topic);
        return sceneryDto;
    }

    @PostMapping("/scenery/update")
    public Boolean updateScenery(@RequestBody SceneryDto sceneryDto){
        SceneryEntity sceneryEntity = new SceneryEntity();
        sceneryEntity.setId(sceneryDto.getId());
        sceneryEntity.setName(sceneryDto.getName());
        sceneryEntity.setGrade(sceneryDto.getGrade());
        sceneryEntity.setAddress(sceneryDto.getAddress());
        sceneryEntity.setProfile(sceneryDto.getProfile());
        sceneryEntity.setTicketPrice(sceneryDto.getTicketPrice());
        sceneryEntity.setAttention(sceneryDto.getAttention());
        sceneryEntity.setStatus(sceneryDto.getStatus());
        sceneryEntity.setDescription(sceneryDto.getDescription());
        sceneryEntity.setImageUrl(sceneryDto.getImageUrl());
        Boolean b1 = sceneryService.updateScenery(sceneryEntity);
        topicService.deleteTopicAndSceneryBySceneryId(sceneryDto.getId());
        sceneryService.deleteImgAndSceneryBySceneryId(sceneryDto.getId());
        Boolean b2 = topicService.insertTopic(sceneryDto.getId(), sceneryDto.getTopic());
        Boolean b3 = sceneryService.insertImgs(sceneryDto.getId(), sceneryDto.getUrls());
        return b1&&b2&&b3;
    }
}

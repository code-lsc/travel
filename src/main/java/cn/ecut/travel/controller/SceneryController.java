package cn.ecut.travel.controller;

import cn.ecut.travel.dto.Scenery;
import cn.ecut.travel.entity.SceneryEntity;
import cn.ecut.travel.service.SceneryService;
import cn.ecut.travel.vo.SceneryDetailVo;
import cn.ecut.travel.vo.SceneryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scenery")
public class SceneryController {

    @Autowired
    private SceneryService sceneryService;

    @GetMapping("/all")
    public List<SceneryVo> selectScenery(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return sceneryService.selectScenery(page,size);
    }

    @GetMapping("/condition")
    public List<SceneryVo> selectSceneryByCondition(@RequestParam(value = "grade", required = false) Integer grade,
                                                    @RequestParam(value = "topicName", required = false) Integer topicId,
                                                    @RequestParam(value = "addressType", required = false) String addressType,
                                                    @RequestParam(value = "addressValue", required = false) String addressValue){
        Map<String,Object> map = new HashMap<>();
        map.put("grade",grade);
        map.put("topicId",topicId);
        map.put("addressType",addressType);
        map.put("addressValue",addressValue);
        if ("".equals(addressType))
            map.put("addressType",null);
        if ("".equals(addressValue))
            map.put("addressValue",null);
        return sceneryService.selectSceneryByConditions(map);
    }

    @GetMapping("/name")
    public List<String> selectAllName(){
        return sceneryService.selectAllSceneryName();
    }

    @GetMapping("/name_id")
    public List<Scenery> selectIdAndName(){
        return sceneryService.selectIdAndName();
    }

    @GetMapping("/detail")
    public SceneryDetailVo getSceneryDetailInfo(@RequestParam("sceneryId") Integer sceneryId){
        return sceneryService.selectSceneryDetail(sceneryId);
    }

    @GetMapping("/select")
    public List<SceneryVo> selectSceneryByKey(@RequestParam("key") String key){
        return sceneryService.selectSceneryByKey(key);
    }

    @GetMapping("/carousel")
    public List<String> getImg(){
        return sceneryService.selectIndexImg();
    }

    @PostMapping("/carousel/add")
    public void addImg(@RequestBody List<String> imgs){
        sceneryService.addIndexImg(imgs);
    }

    @GetMapping("/select/all")
    public List<SceneryEntity> getAllScenery(@RequestParam("key") String key){
        return sceneryService.selectAllSceneryByKey(key);
    }


}

package cn.ecut.travel.controller;


import cn.ecut.travel.service.SceneryService;
import cn.ecut.travel.vo.IndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scenery")
public class IndexController {

    @Autowired
    private SceneryService sceneryService;

    @GetMapping("/countAll")
    public Integer sceneryCount(){
        return sceneryService.countAllScenery();
    }

    @GetMapping("/hotScenery")
    public List<IndexVo> hotScenery(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        List<IndexVo> indexVos = sceneryService.selectHotScenery(page, size);
        return indexVos;
    }

    @GetMapping("/hotCityScenery")
    public List<IndexVo> hotCityScenery(@RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size,
                                        @RequestParam("city") String city){
        List<IndexVo> indexVos = sceneryService.selectSceneryByCity(page, size, city);
        return indexVos;
    }

    @GetMapping("/search")
    public List<IndexVo> searchScenery(@RequestParam("key") String key){
        return sceneryService.selectIndexSceneryByKey(key);
    }



}

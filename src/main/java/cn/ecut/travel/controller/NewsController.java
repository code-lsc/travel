package cn.ecut.travel.controller;

import cn.ecut.travel.entity.NewsEntity;
import cn.ecut.travel.service.NewsService;
import cn.ecut.travel.vo.NewsDetailVo;
import cn.ecut.travel.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/count")
    public Integer countNews(){
        return newsService.selectNewCount();
    }

    @GetMapping("all")
    public List<NewsVo> getAllNews(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
        return newsService.selectAllNews(page, size);
    }

    @GetMapping("/detail")
    public NewsDetailVo getNewsDetail(@RequestParam("id") Integer id){
        return newsService.selectNewsById(id);
    }

    @PostMapping("/add")
    public Boolean addNews(@RequestBody NewsEntity newsEntity){
        newsEntity.setStatus(1);
        newsEntity.setCreateTime(new Date());
        return newsService.insertNews(newsEntity);
    }

    @GetMapping("/getAll")
    public List<NewsVo> getAllNews(){
        return newsService.selectNews();
    }

    @GetMapping("/delete")
    public Boolean deleteNews(@RequestParam("status") Integer status,@RequestParam("id") Integer id){
        return newsService.deleteNews(status,id);
    }

    @GetMapping("/search")
    public Map<String,Object> getNewsByKey(@RequestParam("key") String key,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size){
        Integer count = newsService.selectNewsCountByKey(key);
        List<NewsVo> newsVos = newsService.selectAllNewsByKey(key,page,size);
        Map map = new HashMap();
        map.put("count",count);
        map.put("news",newsVos);
        return map;
    }

    @GetMapping("/select")
    public List<NewsVo> selectNewsByKey(@RequestParam("key") String key){
        return newsService.selectNewsByKey(key);
    }
}

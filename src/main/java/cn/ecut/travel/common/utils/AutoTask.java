package cn.ecut.travel.common.utils;

import cn.ecut.travel.common.constants.ScoreConstants;
import cn.ecut.travel.dao.SceneryDao;
import cn.ecut.travel.dto.Scenery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class AutoTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SceneryDao sceneryDao;

    @Scheduled(fixedRate = 1000*60*10) //10分钟执行一次
    public void yourTaskMethod() {
        List<Scenery> sceneries = sceneryDao.selectIdAndName();
        List<Map<String,Object>> list = new LinkedList<>();
        for (Scenery scenery : sceneries) {
            Map<String,Object> map = new HashMap<>();
            String key = String.format(ScoreConstants.SCENERY_SCORE,scenery.getId());
            Object scores = redisTemplate.opsForHash().get(key, "score");
            Object count = redisTemplate.opsForHash().get(key, "count");
            Double score=0D;
            if (count!=null){
                score=Integer.parseInt(String.valueOf(scores))*1.0/Integer.parseInt(String.valueOf(count));
            }
            map.put("key", scenery.getId());
            map.put("value",score);
            list.add(map);

        }
        sceneryDao.updateScore(list);
    }
}

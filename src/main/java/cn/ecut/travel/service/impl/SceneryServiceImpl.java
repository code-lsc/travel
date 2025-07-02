package cn.ecut.travel.service.impl;

import cn.ecut.travel.common.constants.CommonConstants;
import cn.ecut.travel.dao.SceneryDao;
import cn.ecut.travel.dto.Scenery;
import cn.ecut.travel.dto.SceneryDto;
import cn.ecut.travel.entity.SceneryEntity;
import cn.ecut.travel.service.CommentService;
import cn.ecut.travel.service.SceneryService;
import cn.ecut.travel.vo.IndexVo;
import cn.ecut.travel.vo.SceneryCommentVo;
import cn.ecut.travel.vo.SceneryDetailVo;
import cn.ecut.travel.vo.SceneryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SceneryServiceImpl implements SceneryService {

    @Autowired
    private SceneryDao sceneryDao;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer countAllScenery() {
        return sceneryDao.countAllScenery();
    }

    @Override
    public List<IndexVo> selectHotScenery(Integer page, Integer size) {
        return sceneryDao.selectSceneryByScore(page,size);
    }

    @Override
    public List<IndexVo> selectSceneryByCity(Integer page, Integer size, String city) {
        return sceneryDao.selectSceneryByCity(page, size, city);
    }

    @Override
    public List<SceneryVo> selectScenery(Integer page, Integer size) {
        return sceneryDao.selectScenery(page, size);
    }

    @Override
    public List<SceneryVo> selectSceneryByConditions(Map<String, Object> params) {
        return sceneryDao.selectSceneryByConditions(params);
    }

    @Override
    public List<String> selectAllSceneryName() {
        return sceneryDao.selectAllSceneryName();
    }

    @Override
    public List<Scenery> selectIdAndName() {
        return sceneryDao.selectIdAndName();
    }

    @Override
    public SceneryDetailVo selectSceneryDetail(Integer sceneryId) {
        SceneryDetailVo sceneryDetailVos = sceneryDao.selectSceneryDetail(sceneryId);
        List<SceneryCommentVo> sceneryCommentVos = commentService.selectCommentBySceneryId(sceneryId);
        sceneryDetailVos.setComment(sceneryCommentVos);
       /* String key = String.format(ScoreConstants.SCENERY_SCORE,sceneryId);
        Object scores = redisTemplate.opsForHash().get(key, "score");
        Object count = redisTemplate.opsForHash().get(key, "count");
        Double score=0D;
        if (count!=null){
            score=Integer.parseInt(String.valueOf(scores))*1.0/Integer.parseInt(String.valueOf(count));
        }
        sceneryDetailVos.setScore(score);*/
        return sceneryDetailVos;
    }

    @Override
    public List<IndexVo> selectIndexSceneryByKey(String key) {
        return sceneryDao.selectIndexSceneryByKey(key);
    }

    @Override
    public List<SceneryVo> selectSceneryByKey(String key) {
        return sceneryDao.selectSceneryByKey(key);
    }

    @Override
    public List<String> selectIndexImg() {
        Object img = redisTemplate.opsForValue().get(CommonConstants.CAROUSEL_KEY);
        List imgs = (List) img;
        return imgs;
    }

    @Override
    public void addIndexImg(List<String> img) {
         redisTemplate.opsForValue().set(CommonConstants.CAROUSEL_KEY,img);
    }

    @Override
    public Boolean insertScenery(SceneryEntity sceneryEntity) {
        return sceneryDao.insertScenery(sceneryEntity)>0?true:false;
    }

    @Override
    public Integer selectSceneryIdByProfile(String profile) {
        return sceneryDao.selectSceneryIdByProfile(profile);
    }

    @Override
    public Boolean insertImgs(Integer sceneryId, List<String> urls) {
        return sceneryDao.insertImgs(sceneryId, urls)>0?true:false;
    }

    @Override
    public List<SceneryEntity> selectAllScenery() {
        return sceneryDao.selectAllScenery();
    }

    @Override
    public SceneryDto selectSceneryById(Integer sceneryId) {
        return sceneryDao.selectSceneryById(sceneryId);
    }

    @Override
    public List<String> selectImgsBySceneryId(Integer sceneryId) {
        return sceneryDao.selectImgsBySceneryId(sceneryId);
    }

    @Override
    public Boolean deleteImgAndSceneryBySceneryId(Integer sceneryId) {
        return sceneryDao.deleteImgAndSceneryBySceneryId(sceneryId)>0?true:false;
    }

    @Override
    public Boolean updateScenery(SceneryEntity sceneryEntity) {
        return sceneryDao.updateScenery(sceneryEntity)>0?true:false;
    }

    @Override
    public List<SceneryEntity> selectAllSceneryByKey(String key) {
        return sceneryDao.selectAllSceneryByKey(key);
    }
}

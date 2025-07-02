package cn.ecut.travel.service;


import cn.ecut.travel.dto.Scenery;
import cn.ecut.travel.dto.SceneryDto;
import cn.ecut.travel.entity.SceneryEntity;
import cn.ecut.travel.vo.IndexVo;
import cn.ecut.travel.vo.SceneryDetailVo;
import cn.ecut.travel.vo.SceneryVo;

import java.util.List;
import java.util.Map;

public interface SceneryService {

    Integer countAllScenery();

    List<IndexVo> selectHotScenery(Integer page, Integer size);

    List<IndexVo> selectSceneryByCity(Integer page, Integer size,String city);

    List<SceneryVo> selectScenery(Integer page, Integer size);

    List<SceneryVo> selectSceneryByConditions(Map<String, Object> params);

    List<String> selectAllSceneryName();

    List<Scenery> selectIdAndName();

    SceneryDetailVo selectSceneryDetail(Integer sceneryId);

    List<IndexVo> selectIndexSceneryByKey(String key);

    List<SceneryVo> selectSceneryByKey(String key);

    List<String> selectIndexImg();

    void addIndexImg(List<String> img);

    Boolean insertScenery(SceneryEntity sceneryEntity);

    Integer selectSceneryIdByProfile(String profile);

    Boolean insertImgs( Integer sceneryId, List<String> urls);

    List<SceneryEntity> selectAllScenery();

    SceneryDto selectSceneryById(Integer sceneryId);

    List<String> selectImgsBySceneryId(Integer sceneryId);

    Boolean deleteImgAndSceneryBySceneryId(Integer sceneryId);

    Boolean updateScenery(SceneryEntity sceneryEntity);

    List<SceneryEntity> selectAllSceneryByKey(String key);


}

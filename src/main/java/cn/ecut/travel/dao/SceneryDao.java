package cn.ecut.travel.dao;

import cn.ecut.travel.dto.Scenery;
import cn.ecut.travel.dto.SceneryDto;
import cn.ecut.travel.entity.SceneryEntity;
import cn.ecut.travel.vo.IndexVo;
import cn.ecut.travel.vo.SceneryDetailVo;
import cn.ecut.travel.vo.SceneryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SceneryDao {

    /**
     * 统计风景数量
     * @return
     */
    Integer countAllScenery();

    /**
     * 分页查询热门风景
     * @param page
     * @param size
     * @return
     */
    List<IndexVo> selectSceneryByScore(@Param("page") Integer page, @Param("size") Integer size);

    /**
     * 根据城市查找景点
     * @param page
     * @param size
     * @param city
     * @return
     */
    List<IndexVo> selectSceneryByCity(@Param("page") Integer page,
                                      @Param("size") Integer size,
                                      @Param("city") String city);

    /**
     * 查询推荐风景
     * @param page
     * @param size
     * @return
     */
    List<SceneryVo> selectScenery(@Param("page") Integer page, @Param("size") Integer size);

    /**
     * 根据条件动态查询风景
     * @param params
     * @return
     */
    List<SceneryVo> selectSceneryByConditions(Map<String, Object> params);

    /**
     * 查询所有风景名
     * @return
     */
    List<String> selectAllSceneryName();

    /**
     * 查询所有风景id和名字
     * @return
     */
    List<Scenery> selectIdAndName();

    /**
     * 查询景点详情信息
     * @param sceneryId
     * @return
     */
    SceneryDetailVo selectSceneryDetail(@Param("sceneryId") Integer sceneryId);

    void updateScore (@Param("idScoreMap") List<Map<String,Object>>idScoreMap);

    /**
     * 首页搜索风景
     * @param key
     * @return
     */
    List<IndexVo> selectIndexSceneryByKey(@Param("key") String key);

    List<SceneryVo> selectSceneryByKey(@Param("key") String key);

    Integer insertScenery(SceneryEntity sceneryEntity);

    Integer selectSceneryIdByProfile(@Param("profile") String profile);

    Integer insertImgs(@Param("sceneryId") Integer sceneryId, @Param("urls") List<String> urls);

    List<SceneryEntity> selectAllScenery();

    SceneryDto selectSceneryById(@Param("sceneryId") Integer sceneryId);

    List<String> selectImgsBySceneryId(@Param("sceneryId") Integer sceneryId);

    Integer deleteImgAndSceneryBySceneryId(@Param("sceneryId") Integer sceneryId);

    Integer updateScenery(SceneryEntity sceneryEntity);

    List<SceneryEntity> selectAllSceneryByKey(@Param("key") String key);


}

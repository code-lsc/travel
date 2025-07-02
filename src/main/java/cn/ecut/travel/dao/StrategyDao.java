package cn.ecut.travel.dao;

import cn.ecut.travel.entity.StrategyEntity;
import cn.ecut.travel.vo.StrategyDetailVo;
import cn.ecut.travel.vo.StrategySettingVo;
import cn.ecut.travel.vo.StrategyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StrategyDao {

    /**
     *添加攻略信息
      * @param strategy
     * @return
     */
    Integer insertStrategy(StrategyEntity strategy);

    /**
     * 查询攻略的主键
     * @param uid
     * @param imageUrl
     * @return
     */
    Integer selectKey(@Param("uid") Integer uid, @Param("imageUrl")String imageUrl);

    /**
     * 查找攻略信息
     * @param sceneryName
     * @return
     */
    List<StrategyVo> selectStrategyAllOrBySceneryName(@Param("sceneryName") String sceneryName,
                                                      @Param("page") Integer page,
                                                      @Param("size") Integer size);

    /**
     * 获取攻略的数目
     * @return
     */
    Long getStrategyCount(@Param("sceneryName") String sceneryName);

    StrategyDetailVo  selectStrategyById(@Param("strategyId") Integer strategyId);

    Integer deleteStrategyById(@Param("strategyId") Integer strategyId);

    List<StrategyVo> selectStrategyByKey(@Param("key") String key);

    List<StrategySettingVo> selectStrategyByUid(@Param("uid") Integer uid);
}

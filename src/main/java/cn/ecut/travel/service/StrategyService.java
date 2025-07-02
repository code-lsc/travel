package cn.ecut.travel.service;

import cn.ecut.travel.entity.StrategyEntity;
import cn.ecut.travel.vo.StrategyDetailVo;
import cn.ecut.travel.vo.StrategySettingVo;
import cn.ecut.travel.vo.StrategyVo;

import java.util.List;

public interface StrategyService {

    Boolean addStrategy(StrategyEntity strategy);

    Integer selectKey(Integer uid, String imageUrl);

    List<StrategyVo> selectAllStrategy(Integer uid,String sceneryName,Integer page,Integer size);

    Long getStrategyCount(String sceneryName);

    StrategyDetailVo getStrategyDetailInfoById(Integer strategyId,Integer uid);

    Boolean deleteStrategyById(Integer strategyId);

    List<StrategyVo> selectStrategyByKey(String key,Integer uid);

    List<StrategySettingVo> selectStrategyByUid(Integer uid);



}

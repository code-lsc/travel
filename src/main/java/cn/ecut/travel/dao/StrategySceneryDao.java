package cn.ecut.travel.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StrategySceneryDao {

    void insertStrategyScenery(@Param("strategyId") Integer strategyId, @Param("sceneryIds") List<Integer> sceneryIds);
}

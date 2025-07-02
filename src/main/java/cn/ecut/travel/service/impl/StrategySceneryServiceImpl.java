package cn.ecut.travel.service.impl;

import cn.ecut.travel.dao.StrategySceneryDao;
import cn.ecut.travel.service.StrategySceneryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StrategySceneryServiceImpl implements StrategySceneryService {

    @Autowired
    private StrategySceneryDao strategySceneryDao;

    @Override
    public void addStrategyAndSceneryLink(Integer strategyId, List<Integer> sceneryIds) {
        strategySceneryDao.insertStrategyScenery(strategyId, sceneryIds);
    }
}

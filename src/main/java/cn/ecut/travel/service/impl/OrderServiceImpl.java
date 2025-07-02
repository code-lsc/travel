package cn.ecut.travel.service.impl;

import cn.ecut.travel.common.constants.ScoreConstants;
import cn.ecut.travel.dao.OrderDao;
import cn.ecut.travel.entity.OrderEntity;
import cn.ecut.travel.entity.SceneryComment;
import cn.ecut.travel.service.OrderService;
import cn.ecut.travel.vo.OrderAdminVo;
import cn.ecut.travel.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Boolean addOrder(OrderEntity order) {
        return orderDao.addOrder(order)>0?true:false;
    }

    @Override
    public List<OrderVo> selectAllOrder(Integer userId) {
        return orderDao.selectOrderByUserId(userId);
    }

    @Override
    public Boolean addOrderComment(SceneryComment sceneryComment) {
        String key=String.format(ScoreConstants.SCENERY_SCORE,orderDao.selectSceneryIdByOrderId(sceneryComment.getOrderId()));
        redisTemplate.opsForHash().increment(key, "score", sceneryComment.getScore());
        redisTemplate.opsForHash().increment(key, "count", 1); // 人数加1
        return orderDao.addOrderComment(sceneryComment)>0?true:false;
    }

    @Override
    public Boolean updateOrderStatus(Integer status,Integer orderId) {
        return orderDao.updateOrderStatus(status,orderId)>0?true:false;
    }

    @Override
    public List<OrderAdminVo> selectAllOrder() {
        return orderDao.selectAllOrder();
    }

    @Override
    public Integer deleteTicket(Integer id) {
        return orderDao.deleteTicket(id);
    }


}

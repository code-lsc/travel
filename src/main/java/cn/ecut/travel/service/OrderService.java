package cn.ecut.travel.service;

import cn.ecut.travel.entity.OrderEntity;
import cn.ecut.travel.entity.SceneryComment;
import cn.ecut.travel.vo.OrderAdminVo;
import cn.ecut.travel.vo.OrderVo;

import java.util.List;

public interface OrderService {

    Boolean addOrder(OrderEntity order);

    List<OrderVo> selectAllOrder(Integer userId);

    Boolean  addOrderComment(SceneryComment sceneryComment);

    Boolean updateOrderStatus(Integer status,Integer orderId);

    List<OrderAdminVo> selectAllOrder();

    Integer deleteTicket(Integer id);

}

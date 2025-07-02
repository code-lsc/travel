package cn.ecut.travel.dao;

import cn.ecut.travel.entity.OrderEntity;
import cn.ecut.travel.entity.SceneryComment;
import cn.ecut.travel.vo.OrderAdminVo;
import cn.ecut.travel.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {

    Integer addOrder(OrderEntity order);

    List<OrderVo> selectOrderByUserId(@Param("userId") Integer userId);

    /**
     * 添加订单评价
     * @param sceneryComment
     * @return
     */
    Integer addOrderComment(SceneryComment sceneryComment);

    /**
     * 根据订单id查询风景id
     * @param orderId
     * @return
     */
    Integer selectSceneryIdByOrderId(@Param("orderId") Integer orderId);

    /**
     * 修改订单状态
     * @param status
     * @return
     */
    Integer updateOrderStatus(@Param("status") Integer status,@Param("orderId") Integer orderId);

    List<OrderAdminVo> selectAllOrder();

    Integer deleteTicket(@Param("id") Integer id);
}

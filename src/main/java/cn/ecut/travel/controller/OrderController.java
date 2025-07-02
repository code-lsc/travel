package cn.ecut.travel.controller;

import cn.ecut.travel.common.exception.TravelException;
import cn.ecut.travel.common.help.UserHelp;
import cn.ecut.travel.entity.OrderEntity;
import cn.ecut.travel.entity.SceneryComment;
import cn.ecut.travel.service.OrderService;
import cn.ecut.travel.vo.OrderAdminVo;
import cn.ecut.travel.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserHelp userHelp;

    @PostMapping("/add")
    public Boolean buyTicket(@RequestBody OrderEntity order){
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        order.setUid(userHelp.get().getId());
        order.setCreateTime(new Date());
        return orderService.addOrder(order);
    }

    @GetMapping("/all")
    public List<OrderVo> getAllOrder(){
        if (userHelp.get()==null)
            throw new TravelException("用户未登录");
        return orderService.selectAllOrder(userHelp.get().getId());
    }

    @PostMapping("/comment")
    public Boolean addOrderComment(@RequestBody SceneryComment sceneryComment){
        sceneryComment.setCreateTime(new Date());
        sceneryComment.setStatus(1);
        Boolean bool1 = orderService.addOrderComment(sceneryComment);
        Boolean bool2 = orderService.updateOrderStatus(2, sceneryComment.getOrderId());
        return bool1&&bool2;
    }

    @GetMapping("/getAll")
    public List<OrderAdminVo> selectAllOrder(){
        if (userHelp.get()==null && userHelp.get().getType()!=1)
            throw new TravelException("管理员未登录");
        return orderService.selectAllOrder();
    }

    @GetMapping("/delete")
    public void deleteOrder(@RequestParam("id") Integer id){
        orderService.deleteTicket(id);
    }

    @GetMapping("/status")
    public void updateOrderStatus(@RequestParam("id") Integer id){
        orderService.updateOrderStatus(1,id);
    }
}

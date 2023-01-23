package com.increff.pos.flow;

import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Objects;

public class OrderFlow {
    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryService inventoryService;
    @Transactional(rollbackOn = ApiException.class)
    public void addOrderItem(OrderItemPojo p) throws ApiException {
        InventoryPojo a = inventoryService.getCheck(p.getProductId());
        if(p.getQuantity()>a.getQuantity()){
            throw new ApiException("Item can't be added to order as it exceeds the inventory. Present inventory count : "+a.getQuantity());
        }
        inventoryService.addSub(a,-p.getQuantity());
        orderService.addOrderItem(p);
    }


    @Transactional(rollbackOn = ApiException.class)
    public void deleteOrderItem(int id) throws ApiException {
        OrderItemPojo ex = orderService.getCheckOrderItem(id);
        InventoryPojo a = inventoryService.getCheck(ex.getProductId());
        inventoryService.addSub(a,ex.getQuantity());
        orderService.deleteOrderItem(id);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void updateOrderItem(int id, OrderItemPojo p) throws ApiException {
        OrderItemPojo ex = orderService.getCheckOrderItem(id);
        InventoryPojo a = inventoryService.getCheck(ex.getProductId());
        inventoryService.addSub(a,-p.getQuantity()+ex.getQuantity());
        orderService.updateOrderItem(ex,p);
    }


}

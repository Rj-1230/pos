package com.increff.pos.flow;

import com.increff.pos.pojo.CartPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.CartService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

import static com.increff.pos.helper.CartFlowHelper.convertCartPojoToOrderItemPojo;

public class CartFlow {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(CartPojo cartPojo) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.getCheck(cartPojo.getProductId());
        if (cartPojo.getQuantity() > inventoryPojo.getQuantity()) {
            throw new ApiException("Item can't be added to cart as it exceeds the inventory. Present inventory count : " + inventoryPojo.getQuantity());
        }
        cartService.add(cartPojo,inventoryPojo);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, CartPojo newCartPojo) throws ApiException {
        CartPojo exCartPojo = cartService.getCheck(id);
        InventoryPojo inventoryPojo = inventoryService.getCheck(exCartPojo.getProductId());
        if(newCartPojo.getQuantity()>inventoryPojo.getQuantity()){
            throw new ApiException("Item can't be updated as it exceeds the inventory. Present inventory count : "+inventoryPojo.getQuantity());
        }
        cartService.update(exCartPojo,newCartPojo);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void pushToNewOrder(List<CartPojo> cartPojoList, OrderPojo orderPojo) throws ApiException {
        if(cartPojoList.size()==0){
            throw new ApiException("The order can't be created as the cart is empty");
        }
        for(CartPojo d : cartPojoList){
            InventoryPojo a = inventoryService.getCheck(d.getProductId());
            if(d.getQuantity()>a.getQuantity()){
                throw new ApiException("The item "+d.getProductName()+" can't be added to order because sufficient amount not present in inventory. Inventory count = "+a.getQuantity()+"Cart count ="+d.getQuantity());
            }
        }
        int orderId = orderService.addOrder(orderPojo);
        for(CartPojo cartPojo : cartPojoList){
            OrderItemPojo orderItemPojo = convertCartPojoToOrderItemPojo(cartPojo,orderId);
            orderService.addOrderItem(orderItemPojo);
        }

        cartService.deleteAll();
    }

}

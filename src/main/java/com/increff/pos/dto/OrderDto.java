package com.increff.pos.dto;

import com.increff.pos.flow.OrderFlow;
import com.increff.pos.model.*;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.GetCurrentTime.getCurrentDateTime;
import static com.increff.pos.helper.NullCheckHelper.*;
import static com.increff.pos.helper.OrderDtoHelper.*;
import static com.increff.pos.helper.OrderItemDtoHelper.*;
import static com.increff.pos.helper.OrderItemDtoHelper.getAllOrderItemsOfAgivenOrder;

@Service

public class OrderDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderFlow orderFlow;
    @Autowired
    private ProductService productService;


    public OrderData getOrderDetails(int id) throws ApiException {
        OrderPojo p = orderService.getCheckOrder(id);
        return convert(p);
    }

    public void updateOrder(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        OrderPojo o = convert(f);
        orderService.updateCustomerDetails(id,o);
    }

    public List<OrderData> getAllOrdersByCounterId(){
        return getAllOrders(orderService.getAllOrdersByCounterId());
    }

    public List<OrderData> getAll(){
        return getAllOrders(orderService.getAll());
    }


    public void placeOrder(int id) throws ApiException
    {
        orderService.placeOrder(id);
    }

    public void addOrderItem(OrderItemForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        ProductPojo p= productService.getProductPojoFromBarcode(f.getBarcode());
        OrderItemPojo o = convert(f,p);
        orderFlow.addOrderItem(o);
    }

    public void deleteOrderItem(@PathVariable int id) throws ApiException {
        orderFlow.deleteOrderItem(id);
    }

    public OrderItemData getOrderItem(int id) throws ApiException {

        OrderItemPojo p = orderService.getCheckOrderItem(id);
        return convert(p);
    }

    public void updateOrderItem(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        OrderItemPojo o = convert(f);
        orderFlow.updateOrderItem(id,o);
    }

    public List<OrderItemData> getAllOrderItems(int orderId){
        return getAllOrderItemsOfAgivenOrder(orderService.getAllOrderItems(orderId));
    }



}

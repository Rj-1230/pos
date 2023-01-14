package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.OrderPojo;
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

@Service

public class OrderDto {
    @Autowired
    private OrderService orderService;

    public int addOrder(OrderForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        OrderPojo o = convert(f);
        return  orderService.addOrder(o);
    }

    public void deleteOrder(@PathVariable int id){
        orderService.deleteOrder(id);
    }

    public OrderData getOrderDetails(int id) throws ApiException {
        OrderPojo p = orderService.getOrderDetails(id);
        return convert(p);
    }

    public void updateOrder(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        OrderPojo o = convert(f);
        orderService.updateOrder(id,o);
    }

    public List<OrderData> getAll(){
        return getAllOrders(orderService.getAll());
    }

    public List<OrderData> getDateFilter(DateFilterForm f) throws ApiException {
        return ordersInDateRange(orderService.selectDateFilter(f));
    }

    public void placeOrder(int id) throws ApiException
    {
        orderService.placeOrder(id);
    }




}

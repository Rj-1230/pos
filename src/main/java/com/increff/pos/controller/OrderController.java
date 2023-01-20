package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api

public class OrderController {

    @Autowired
    private OrderDto orderDto;
    @ApiOperation(value = "Updating details of a particular Order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    public void updateOrder(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        orderDto.updateOrder(id, f);
    }

    @ApiOperation(value = "Getting details of all the orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        return orderDto.getAll();
    }

    @ApiOperation(value = "Mark order placed")
    @RequestMapping(path = "api/order/place/{id}", method = RequestMethod.PUT)
    public void markOrderPlaced(@PathVariable int id) throws ApiException {
        orderDto.placeOrder(id);
    }

    @ApiOperation(value="Adding a orderItem")
    @RequestMapping(path="/api/orderItem", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm f)throws ApiException{
        orderDto.addOrderItem(f);
    }


    @ApiOperation(value="Deleting a orderItem")
    @RequestMapping(path="/api/orderItem/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        orderDto.deleteOrderItem(id);
    }

    @ApiOperation(value="Getting details of a orderItem from order item ID")
    @RequestMapping(path="/api/orderItem/{id}", method = RequestMethod.GET)
    public OrderItemData get(@PathVariable int id) throws ApiException {
        return orderDto.getOrderItem(id);
    }

    @ApiOperation(value="Updating details of a particular OrderItem")
    @RequestMapping(path="/api/orderItem/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
        orderDto.updateOrderItem(id,f);
    }

    @ApiOperation(value="Getting details of all the order with given order id")
    @RequestMapping(path="/api/orderItems/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getAll(@PathVariable int id){
        return orderDto.getAllOrderItems(id);
    }


}

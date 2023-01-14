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

    @ApiOperation(value = "Adding a order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void add(@RequestBody OrderForm f) throws ApiException {
        orderDto.addOrder(f);
    }

    //    Delete order is never used
    @ApiOperation(value = "Deleting a order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        orderDto.deleteOrder(id);
    }

    @ApiOperation(value = "Getting details of a order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int id) throws ApiException {
        return orderDto.getOrderDetails(id);
    }

    @ApiOperation(value = "Updating details of a particular Order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        orderDto.updateOrder(id, f);
    }

    @ApiOperation(value = "Select all order between given date")
    @RequestMapping(path = "/api/order/dateFilter", method = RequestMethod.POST)
    public List<OrderData> getOrdersFromDateFilter(@RequestBody DateFilterForm f) throws ApiException {
        return orderDto.getDateFilter(f);
    }

    @ApiOperation(value = "Getting details of all the orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAllOrders() {
        return orderDto.getAll();
    }

    @ApiOperation(value = "Mark order placed")
    @RequestMapping(path = "api/order/place/{id}", method = RequestMethod.PUT)
    public void markOrderPlaced(@PathVariable int id) throws ApiException {
        orderDto.placeOrder(id);
    }

}

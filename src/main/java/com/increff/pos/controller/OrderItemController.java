package com.increff.pos.controller;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api

public class OrderItemController {

    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value="Adding a orderItem")
    @RequestMapping(path="/api/orderItem", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm f)throws ApiException{
            orderItemDto.add(f);
    }


    @ApiOperation(value="Deleting a orderItem")
    @RequestMapping(path="/api/orderItem/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        orderItemDto.delete(id);
    }

    @ApiOperation(value="Getting details of a orderItem from order item ID")
    @RequestMapping(path="/api/orderItem/{id}", method = RequestMethod.GET)
    public OrderItemData get(@PathVariable int id) throws ApiException {
        return orderItemDto.get(id);
    }

    @ApiOperation(value="Updating details of a particular OrderItem")
    @RequestMapping(path="/api/orderItem/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody CartEditForm f) throws ApiException {
        orderItemDto.update(id,f);
    }

    @ApiOperation(value="Getting details of all the orderItems")
    @RequestMapping(path="/api/orderItem", method = RequestMethod.GET)
    public List<OrderItemData> getAll(){
        return orderItemDto.getAll();
    }

    @ApiOperation(value="Getting details of all the order with given order id")
    @RequestMapping(path="/api/orderItems/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getAll(@PathVariable int id){
        return orderItemDto.getAll(id);
    }

//    @ApiOperation(value="Editing the customer name of a given order")
//    @RequestMapping(path="/api/orderItems/{id}", method = RequestMethod.PUT)
//    public void updateCustomer(@PathVariable int id, @RequestBody CustomerNameForm f) throws ApiException{
//        orderItemDto.update(id, f);
//        //before returning , we need to convert our OrderPojo type data into OrderData format
//    }

//    @ApiOperation(value="Making order placed by changing status")
//    @RequestMapping(path="/api/orderItemPlace/{id}", method = RequestMethod.PUT)
//    public void updateStatus(@PathVariable int id) throws ApiException{
//        orderItemDto.update(id);
//        //before returning , we need to convert our OrderPojo type data into OrderData format
//    }
}

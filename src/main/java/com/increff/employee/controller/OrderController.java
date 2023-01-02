package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api

public class OrderController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value="Adding a order")
    @RequestMapping(path="/api/order", method = RequestMethod.POST)
    public String add(@RequestBody OrderForm f)throws ApiException{
        String message = "Order added successfully";
        try {
//            OrderPojo p = convert(orderForm);
            dto.add(f);
        }
        catch (Exception e)
        {
            System.out.println("This is exception message" + e);
            message = e.getMessage();
        }

        return message;
    }



    @ApiOperation(value="Deleting a order")
    @RequestMapping(path="/api/order/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        dto.delete(id);
    }

    @ApiOperation(value="Getting details of a order")
    @RequestMapping(path="/api/order/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
        //before returning , we need to convert our OrderPojo type data into OrderData format
    }

    @ApiOperation(value="Updating details of a particular Order")
    @RequestMapping(path="/api/order/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        dto.update(id,f);
        //before returning , we need to convert our OrderPojo type data into OrderData format
    }

    @ApiOperation(value="Getting details of all the orders")
    @RequestMapping(path="/api/order", method = RequestMethod.GET)
    public List<OrderData> getAll(){
        return dto.getAll();
        //before returning , we need to convert our OrderPojo type data into OrderData format
    }

}

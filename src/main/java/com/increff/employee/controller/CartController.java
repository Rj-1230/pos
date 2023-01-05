package com.increff.employee.controller;

import com.increff.employee.dto.CartDto;
import com.increff.employee.model.*;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api

public class CartController {

    @Autowired
    private CartDto dto;

    @ApiOperation(value="Adding an item to the cart")
    @RequestMapping(path="/api/cart", method = RequestMethod.POST)
    public String add(@RequestBody CartForm f)throws ApiException{
        String message = "Item added to cart successfully";
        try {
//            OrderItemPojo p = convert(orderItemForm);
            dto.add(f);
        }
        catch (Exception e)
        {
            System.out.println("This is exception message" + e);
            message = e.getMessage();
        }

        return message;
    }



    @ApiOperation(value="Removing an item from the cart")
    @RequestMapping(path="/api/cart/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        dto.delete(id);
    }

    @ApiOperation(value="Getting details of a particular item in the cart")
    @RequestMapping(path="/api/cart/{id}", method = RequestMethod.GET)
    public CartData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    @ApiOperation(value="Updating details of a particular item in the cart")
    @RequestMapping(path="/api/cart/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody CartEditForm f) throws ApiException {
        dto.update(id,f);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    @ApiOperation(value="Getting details of all the items in the cart")
    @RequestMapping(path="/api/cart", method = RequestMethod.GET)
    public List<CartData> getAll(){
        return dto.getAll();
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

//    @ApiOperation(value="Getting details of all the order with given order id")
//    @RequestMapping(path="/api/orderItems/{id}", method = RequestMethod.GET)
//    public List<OrderItemData> getAll(@PathVariable int id){
//        return dto.getAll(id);
//        //before returning , we need to convert our OrderPojo type data into OrderData format
//    }

//    @ApiOperation(value="Editing the customer name of a given order")
//    @RequestMapping(path="/api/orderItems/{id}", method = RequestMethod.PUT)
//    public void updateCustomer(@PathVariable int id, @RequestBody OrderForm f) throws ApiException{
//        dto.update(id, f);
//        //before returning , we need to convert our OrderPojo type data into OrderData format
//    }
}

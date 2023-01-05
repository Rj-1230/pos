package com.increff.employee.controller;

import com.increff.employee.dto.CartFlushDto;
import com.increff.employee.model.CartForm;
import com.increff.employee.model.OrderForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api
public class CartFlushController {
    @Autowired
    CartFlushDto dto;

    @ApiOperation(value="Adding an item to the cart")
    @RequestMapping(path="/api/cartFlush", method = RequestMethod.POST)
    public String add(@RequestBody OrderForm f)throws ApiException {
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
}

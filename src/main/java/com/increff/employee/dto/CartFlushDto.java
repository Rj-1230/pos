package com.increff.employee.dto;

import com.increff.employee.model.CartData;
import com.increff.employee.model.CartForm;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.CartPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

public class CartFlushDto {

    @Autowired
    private OrderDto orderDto;

    @Autowired
    private CartDto cartDto;

    @Autowired
    private OrderItemService orderItemService;
    public void add(OrderForm f) throws ApiException {
        int id = orderDto.add(f);

//        System.out.println(productId);


        List<CartData>list1= cartDto.getAll();

            for(CartData d : list1){
                OrderItemPojo o = new OrderItemPojo();
                o.setOrderId(id);
                o.setProductName(d.getProductName());
                o.setQuantity(d.getQuantity());
                o.setSellingPrice(d.getSellingPrice());
                orderItemService.add(o);
            }

            cartDto.deleteAll(list1);





    }


//    private static OrderItemPojo convert(OrderForm f){
//        //The convert method will convert JSON format data received into OrderItemPojo format
//        OrderItemPojo p = new OrderItemPojo();
//
//        return p;
//    }
}

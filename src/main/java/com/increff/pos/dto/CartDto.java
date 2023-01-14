package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.CartPojo;

import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.increff.pos.helper.CartDtoHelper.*;
import static com.increff.pos.helper.OrderDtoHelper.*;
import static com.increff.pos.helper.NullCheckHelper.*;

@Service

public class CartDto {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    public void add(CartForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        ProductPojo p= productService.getProductPojoFromBarcode(f.getBarcode());
        CartPojo c = convert(f,p);
        cartService.add(c,p.getProductId());
    }

    public void delete(@PathVariable int id) throws ApiException{
        cartService.delete(id);
    }

    public CartData get(int id) throws ApiException {
        CartPojo c = cartService.get(id);
        return convert(c);
    }

    public void update(@PathVariable int id, @RequestBody CartEditForm f) throws ApiException {
        checkNullable(f);
        CartPojo c = convert(f);
        cartService.update(id,c);
    }
    public List<CartData> getAll(){
        return getAllCartItems(cartService.getAll());
    }


    public void deleteAll(List<CartData> list1) throws ApiException{
        cartService.deleteAll(list1);
    }


    public void pushToNewOrder(OrderForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        List<CartData>list1= getAll();
        OrderPojo p = convert(f);
       cartService.pushToNewOrder(f,list1,p);
    }

    public void flushAll()throws ApiException{
        List<CartData>list1= getAll();
        deleteAll(list1);
    }
}
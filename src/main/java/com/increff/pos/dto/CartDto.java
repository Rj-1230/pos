package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.CartPojo;

import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import com.increff.pos.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.increff.pos.helper.CartDtoHelper.*;
import static com.increff.pos.helper.OrderDtoHelper.*;
import static com.increff.pos.helper.NullCheckHelper.*;
import static com.increff.pos.util.SecurityUtil.getPrincipal;

@Service

public class CartDto {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    public void add(CartForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        ProductPojo productPojo= productService.getProductPojoFromBarcode(f.getBarcode());
        CartPojo cartPojo = convert(f,productPojo);
        cartService.add(cartPojo);
    }

    public void delete(@PathVariable int id) throws ApiException{
        cartService.delete(id);
    }

    public CartData get(int id) throws ApiException {
        CartPojo cartPojo = cartService.getCheck(id);
        return convert(cartPojo);
    }

    public void update(@PathVariable int id, @RequestBody CartForm f) throws ApiException {
        checkNullable(f);
        CartPojo cartPojo = convert(f);
        cartService.update(id,cartPojo);
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
        List<CartData> cartDataList= getAll();
        OrderPojo orderPojo = convert(f);
        cartService.pushToNewOrder(cartDataList,orderPojo);
    }

    public void flushAll()throws ApiException{
        List<CartData>list1= getAll();
        deleteAll(list1);
    }
}
package com.increff.pos.helper;

import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.CartPojo;
import com.increff.pos.pojo.ProductPojo;

import java.util.ArrayList;
import java.util.List;

public class CartDtoHelper {

    public static List<CartData> getAllCartItems(List<CartPojo> list){
        List<CartData> list2 = new ArrayList<CartData>();
        for(CartPojo p: list){
            list2.add(convert(p));
        }
        return list2;
    }


    public static CartPojo convert(CartForm f, ProductPojo p){
        CartPojo c = new CartPojo();
        c.setQuantity(f.getQuantity());
        c.setProductId(p.getProductId());
        c.setProductName(p.getName());
        c.setSellingPrice(p.getMrp());
        c.setCounterId(1);
        return c;
    }

    public static CartPojo convert(CartEditForm f){
        //The convert method will convert JSON format data received into OrderItemPojo format
        CartPojo p = new CartPojo();
        p.setQuantity(f.getQuantity());
        p.setSellingPrice(f.getSellingPrice());
        return p;
    }

    public static CartData convert(CartPojo p){
        CartData d = new CartData();
        d.setProductId(p.getProductId());
        d.setCartItemId(p.getCartItemId());
        d.setProductName(p.getProductName());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        d.setCounterId(p.getCounterId());
        return d;
    }


    public static void normalize(CartForm f) {
        f.setBarcode(f.getBarcode().toLowerCase().trim());
    }
}

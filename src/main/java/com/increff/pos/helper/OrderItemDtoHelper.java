package com.increff.pos.helper;

import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemDtoHelper {
    public static OrderItemPojo convert(OrderItemForm f, ProductPojo p){
        OrderItemPojo o = new OrderItemPojo();
        o.setOrderId(f.getOrderId());
        o.setQuantity(f.getQuantity());
        o.setProductId(p.getProductId());
        o.setSellingPrice(p.getMrp());
        o.setProductName(p.getName());
        return o;
    }

    public static void normalize(OrderItemForm f) {
        f.setBarcode(f.getBarcode().toLowerCase().trim());
    }

    public static OrderItemPojo convert(OrderItemForm f){
        OrderItemPojo p = new OrderItemPojo();
        p.setSellingPrice(f.getSellingPrice());
        p.setQuantity(f.getQuantity());
        return p;
    }

    public static OrderItemData convert(OrderItemPojo p){
        OrderItemData d = new OrderItemData();
        d.setOrderItemId(p.getOrderItemId());
        d.setOrderId(p.getOrderId());
        d.setProductId(p.getProductId());
        d.setProductName(p.getProductName());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        return d;
    }

    public static List<OrderItemData> getAllOrderItems(List<OrderItemPojo> list){
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for(OrderItemPojo p: list){
            list2.add(convert(p));
        }
        return list2;
    }


    public static List<OrderItemData> getAllOrderItemsOfAgivenOrder(List<OrderItemPojo> list) {
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for (OrderItemPojo p : list) {
            if(p.getQuantity()==0)
                continue;
            list2.add(convert(p));
        }
        return list2;
    }
}

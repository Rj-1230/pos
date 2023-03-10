package com.increff.pos.helper;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.GetCurrentTime.getCurrentDateTime;
import static com.increff.pos.util.SecurityUtil.getPrincipal;

@Service
public class OrderDtoHelper {

    public static OrderPojo convert(OrderForm f){
        OrderPojo p = new OrderPojo();
        p.setCustomerPhone(f.getCustomerPhone());
        p.setCustomerName(f.getCustomerName());
        p.setOrderCreateTime(getCurrentDateTime());
        p.setCounterId(getPrincipal().getId());
        p.setStatus("created");
        p.setOrderPlaceTime("");
        return p;
    }

    public static OrderData convert(OrderPojo p){
        OrderData d = new OrderData();
        d.setOrderId(p.getOrderId());
        d.setStatus(p.getStatus());
        d.setCounterId(p.getCounterId());
        d.setOrderCreateTime(p.getOrderCreateTime());
        d.setOrderPlaceTime(p.getOrderPlaceTime());
        d.setCustomerName(p.getCustomerName());
        d.setCustomerPhone(p.getCustomerPhone());
        return d;
    }

    public static void normalize(OrderForm f) {
        f.setCustomerName(f.getCustomerName().toLowerCase().trim());
    }

    public static List<OrderData> getAllOrders(List<OrderPojo> list){
        List<OrderData> list2 = new ArrayList<OrderData>();
        for(OrderPojo p: list){
            list2.add(convert(p));
        }
        return list2;
    }

    public static List<OrderData> ordersInDateRange(List<OrderPojo> list2){
        List<OrderData> list1 = new ArrayList<OrderData>();
        for(OrderPojo p: list2)
            list1.add(convert(p));

        return list1;
    }
}

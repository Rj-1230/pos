package com.increff.employee.dto;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.Order;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class OrderDto {
    @Autowired
    private OrderService service;
    @Autowired
    private ProductService serviceP;

    public void add(OrderForm f) throws ApiException {
        OrderPojo o = convert(f);
        service.add(o);
    }

    public void delete(@PathVariable int id){
        service.delete(id);
    }

    public OrderData get(int id) throws ApiException {
        OrderPojo p = service.get(id);
        return convert(p);
        //before returning , we need to convert our OrderPojo type data into OrderData format
    }

    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        OrderPojo o = convert(f);
        service.update(id,o);
        //before returning , we need to convert our OrderPojo type data into OrderData format
    }

    public List<OrderData> getAll(){
        List<OrderPojo> list = service.getAll();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for(OrderPojo p: list){
            list2.add(convert(p));
        }
        return list2;
        //before returning , we need to convert our OrderPojo type data into OrderData format
    }


//    private ProductPojo getProductIdFromBarcode(OrderForm f) throws ApiException{
////        System.out.println(f.getBrandName() + f.getCategoryName());
//
//        ProductPojo p = serviceP.getProductIdFromBarcode(f.getBarcode());
//        if(p==null){
//            throw new ApiException("The order can't be added as no such brand-category exists !!");
//        }
//        return p;
//    }

    private static OrderPojo convert(OrderForm f){
        //The convert method will convert JSON format data received into OrderItemPojo format
        OrderPojo p = new OrderPojo();
        p.setCustomerName(f.getCustomerName());
        p.setOrderTime(getCurrentTime());
        return p;
    }

    private static OrderData convert(OrderPojo p){
        OrderData d = new OrderData();
        d.setOrderId(p.getOrderId());
        d.setOrderTime(p.getOrderTime());
        d.setCustomerName(p.getCustomerName());
//        System.out.println(getCurrentTime());
//        data.setMessage("Hola !");
        return d;
    }

    private static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = formatter.format(date);
        return strDate;
    }
}

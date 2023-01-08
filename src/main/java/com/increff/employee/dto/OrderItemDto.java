package com.increff.employee.dto;

import com.increff.employee.model.*;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service

public class OrderItemDto {
    @Autowired
    private InventoryService serviceI;
    @Autowired
    private OrderItemService service;

    @Autowired
    private OrderService serviceO;
    @Autowired
    private ProductService serviceP;

    public void add(OrderItemForm f) throws ApiException {
        ProductPojo p= getProductIdFromBarcode(f);
//        System.out.println(productId);
        OrderItemPojo o = convert(f);
        o.setProductId(p.getProductId());
        System.out.println("IN DTO of order ITEM");
        System.out.println(o.getProductId());

        System.out.println("IN DTO of order ITEM");
        o.setSellingPrice(p.getMrp());
        o.setProductName(p.getName());
        serviceI.subFromInventory(p.getProductId(),o.getQuantity());
//        System.out.println("a");
        service.add(o);
    }

    public void delete(@PathVariable int id){
        service.delete(id);
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo p = service.get(id);
        return convert(p);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
        OrderItemPojo o = convert(f);
        ProductPojo p  = getProductIdFromBarcode(f);
        o.setProductId(p.getProductId());
        o.setProductName(p.getName());
        o.setSellingPrice(p.getMrp());
        service.update(id,o);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    public void update(@PathVariable int id, @RequestBody CartEditForm f) throws ApiException {
        OrderItemPojo o = convert(f);
        service.update(id,o);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }
    public void update(@PathVariable int id, @RequestBody CustomerNameForm f) throws ApiException {
        OrderPojo o = convert(f);
        service.update(id,o);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    public void update(@PathVariable int id) throws ApiException {
        serviceO.updateStatus(id);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    public List<OrderItemData> getAll(){
        List<OrderItemPojo> list = service.getAll();
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for(OrderItemPojo p: list){
            list2.add(convert(p));
        }
        return list2;
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    public List<OrderItemData> getAll(int id){
        List<OrderItemPojo> list = service.getAll(id);
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for(OrderItemPojo p: list){
            list2.add(convert(p));
        }
        return list2;
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }


    private ProductPojo getProductIdFromBarcode(OrderItemForm f) throws ApiException{
//        System.out.println(f.getBrandName() + f.getCategoryName());

        ProductPojo p = serviceP.getProductIdFromBarcode(f.getBarcode());
        if(p==null){
            throw new ApiException("The order item can't be added as no such product exists !!");
        }
        return p;
    }

    private static OrderItemPojo convert(OrderItemForm f){
        //The convert method will convert JSON format data received into OrderItemPojo format
        OrderItemPojo p = new OrderItemPojo();
        p.setOrderId(f.getOrderId());
        p.setQuantity(f.getQuantity());
        return p;
    }

    private static OrderItemPojo convert(CartEditForm f){
        //The convert method will convert JSON format data received into OrderItemPojo format
        OrderItemPojo p = new OrderItemPojo();

        p.setQuantity(f.getQuantity());
        return p;
    }

    private static OrderPojo convert(CustomerNameForm f){
        //The convert method will convert JSON format data received into OrderItemPojo format
        OrderPojo p = new OrderPojo();
        p.setCustomerName(f.getCustomerName());
        return p;
    }

    private static OrderItemData convert(OrderItemPojo p){
        OrderItemData d = new OrderItemData();
        d.setOrderItemId(p.getOrderItemId());
        d.setOrderId(p.getOrderId());
        d.setProductId(p.getProductId());
        d.setProductName(p.getProductName());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
//        data.setMessage("Hola !");
        return d;
    }
}

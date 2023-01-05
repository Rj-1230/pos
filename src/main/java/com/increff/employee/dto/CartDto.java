package com.increff.employee.dto;

import com.increff.employee.model.*;
import com.increff.employee.pojo.CartPojo;
//import com.increff.employee.pojo.OrderItemPojo;
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

public class CartDto {
    @Autowired
    private InventoryService serviceI;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService serviceP;

    public void add(CartForm f) throws ApiException {
        ProductPojo p= getProductIdFromBarcode(f);
        System.out.println("Hello aa");
//        System.out.println(productId);
        CartPojo c = convert(f);
        c.setProductName(p.getName());
        c.setSellingPrice(p.getMrp());
        c.setCounterId(1);
//        This cpunter ID is if there will be login and diffferent counters
//        If multiple counters, replace it by enployee ID of that counter and thus those many carts
       serviceI.subFromInventory(p.getProductId(),c.getQuantity());
        System.out.println("Hello bb");
//        System.out.println("a");
        cartService.add(c);
    }

    public void delete(@PathVariable int id){
        cartService.delete(id);
    }

    public CartData get(int id) throws ApiException {
        CartPojo c = cartService.get(id);
        return convert(c);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }

    public void update(@PathVariable int id, @RequestBody CartEditForm f) throws ApiException {
        CartPojo c = convert(f);
        cartService.update(id,c);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }
//    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
//        OrderPojo o = convert(f);
//        service.update(id,o);
//        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
//    }

    public List<CartData> getAll(){
        List<CartPojo> list = cartService.getAll();
        List<CartData> list2 = new ArrayList<CartData>();
        for(CartPojo p: list){
            list2.add(convert(p));
        }
        return list2;
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }


    public void deleteAll(List<CartData> list1){
        cartService.deleteAll(list1);
        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
    }
//    public List<OrderItemData> getAll(int id){
//        List<OrderItemPojo> list = service.getAll(id);
//        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
//        for(OrderItemPojo p: list){
//            list2.add(convert(p));
//        }
//        return list2;
//        //before returning , we need to convert our OrderItemPojo type data into OrderItemData format
//    }


    private ProductPojo getProductIdFromBarcode(CartForm f) throws ApiException{
//        System.out.println(f.getBrandName() + f.getCategoryName());

        ProductPojo p = serviceP.getProductIdFromBarcode(f.getBarcode());
        if(p==null){
            throw new ApiException("The order item can't be added as no such product exists !!");
        }
        return p;
    }

    private static CartPojo convert(CartForm f){
        //The convert method will convert JSON format data received into OrderItemPojo format
        CartPojo p = new CartPojo();
        p.setQuantity(f.getQuantity());
        return p;
    }

    private static CartPojo convert(CartEditForm f){
        //The convert method will convert JSON format data received into OrderItemPojo format
        CartPojo p = new CartPojo();
        p.setQuantity(f.getQuantity());
        return p;
    }

    private static CartData convert(CartPojo p){
        CartData d = new CartData();
        d.setCartItemId(p.getCartItemId());
        d.setProductName(p.getProductName());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        d.setCounterId(p.getCounterId());
//        data.setMessage("Hola !");
        return d;
    }
}

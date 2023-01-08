package com.increff.employee.service;

import com.increff.employee.dao.CartDao;
import com.increff.employee.model.CartData;
import com.increff.employee.pojo.CartPojo;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private InventoryService serviceI;
    @Autowired
    private CartDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(CartPojo p,int id,int quant) throws ApiException {
        serviceI.subFromInventory(id,quant);
        CartPojo b = getCartIdFromProductName(p.getProductName());
        System.out.println("Hey guyss");


        System.out.println("Hey guyss");

        if(b!=null){
            b.setQuantity(b.getQuantity()+quant);
            System.out.println("Pehle se tha cart me");
        }
        else{
            dao.insert(p);

        }
//        System.out.println("b");
    }

    @Transactional(rollbackOn = ApiException.class)
    public CartPojo getCartIdFromProductName(String name) throws ApiException {
       try{
           return dao.getCartIdFromProductName(name);
       }
       catch(Exception e){
            System.out.println(e);
            return null;
        }
//    System.out.println("b");
    }
    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {
        CartPojo ex = getCheck(id);
        serviceI.subFromInventory(ex.getProductId(),-ex.getQuantity());
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public CartPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<CartPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void deleteAll(List<CartData> list1) throws ApiException{
        for(CartData d:list1){
            delete(d.getCartItemId());
        }
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, CartPojo p) throws ApiException {
//        normalize(p);
        CartPojo ex = getCheck(id);
        ex.setSellingPrice(p.getSellingPrice());
        serviceI.subFromInventory(ex.getProductId(),p.getQuantity()-ex.getQuantity());
        System.out.println("Inventory update after edit cart");
//        ex.setCartItemId(p.getCartItemId());
        ex.setQuantity(p.getQuantity());

        dao.update(ex);
    }

    @Transactional
    public CartPojo getCheck(int id) throws ApiException {
        try{
            CartPojo p = dao.select(id);
            System.out.println("Cart me h ye item");
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

}
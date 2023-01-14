package com.increff.pos.service;

import com.increff.pos.dao.CartDao;
import com.increff.pos.model.CartData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.pojo.CartPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartDao cartDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(CartPojo p,int id) throws ApiException {
        InventoryPojo a = inventoryService.get(id);
        if(p.getQuantity()>a.getQuantity()){
            throw new ApiException("Item can't be added to cart as it exceeds the inventory. Present inventory count : "+a.getQuantity());
        }
        CartPojo b = cartDao.getCartIdFromProductName(p.getProductName());
        if(Objects.nonNull(b)){
            b.setQuantity(b.getQuantity()+p.getQuantity());
        }
        else{
            cartDao.insert(p);
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {
        CartPojo ex = getCheck(id);
        cartDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public CartPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<CartPojo> getAll() {
        return cartDao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void deleteAll(List<CartData> list1) throws ApiException{
        for(CartData d:list1){
            delete(d.getCartItemId());
        }
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, CartPojo p) throws ApiException {
        CartPojo ex = getCheck(id);
        InventoryPojo a = inventoryService.get(ex.getProductId());
        if(p.getQuantity()>a.getQuantity()){
            throw new ApiException("Item can't be added to cart as it exceeds the inventory. Present inventory count : "+a.getQuantity());
        }
        ex.setSellingPrice(p.getSellingPrice());
        ex.setQuantity(p.getQuantity());
    }

    @Transactional(rollbackOn  = ApiException.class)
    public CartPojo getCheck(int id) throws ApiException {
            CartPojo p = cartDao.select(id);
            if(!Objects.nonNull(p)){
                throw new ApiException("No such item exists in cart with given Id");
            }
            return p;
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void pushToNewOrder(OrderForm f, List<CartData>list1 , OrderPojo p ) throws ApiException {
        if(list1.size()==0){
            throw new ApiException("The order can't be created as the cart is empty");
        }

        for(CartData d : list1){
            InventoryPojo a = inventoryService.get(d.getProductId());
            if(d.getQuantity()>a.getQuantity()){
                throw new ApiException("The item "+d.getProductName()+" can't be added to order because sufficient amount not present in inventory");
            }
        }

        int orderId = orderService.addOrder(p);

        for(CartData d : list1){
            OrderItemPojo o = new OrderItemPojo();
            o.setOrderId(orderId);
            o.setProductId(d.getProductId());
            o.setProductName(d.getProductName());
            InventoryPojo a = inventoryService.get(d.getProductId());
            inventoryService.addSub(a,false,d.getQuantity());
            o.setQuantity(d.getQuantity());
            o.setSellingPrice(d.getSellingPrice());
            orderItemService.add(o);
        }
        deleteAll(list1);
    }

}
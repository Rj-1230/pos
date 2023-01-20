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

import static com.increff.pos.util.SecurityUtil.getPrincipal;

@Service
public class CartService {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartDao cartDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(CartPojo cartPojo) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(cartPojo.getProductId());
        if(cartPojo.getQuantity()>inventoryPojo.getQuantity()){
            throw new ApiException("Item can't be added to cart as it exceeds the inventory. Present inventory count : "+inventoryPojo.getQuantity());
        }
        CartPojo exCartPojo = cartDao.getCartPojoFromProductIdAndCounterId(cartPojo.getProductId(), cartPojo.getCounterId());
        if(Objects.nonNull(exCartPojo)){
            if(exCartPojo.getQuantity()+cartPojo.getQuantity()>inventoryPojo.getQuantity()){
                throw new ApiException("Item can't be added to cart as it exceeds the inventory. Items already in cart : "+exCartPojo.getQuantity() +" Present inventory count :"+inventoryPojo.getQuantity());
            }
            exCartPojo.setQuantity(exCartPojo.getQuantity()+cartPojo.getQuantity());
        }
        else{
            cartDao.insert(cartPojo);
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {
        getCheck(id);
        cartDao.delete(id);
    }

    @Transactional
    public List<CartPojo> getAll() {
        return cartDao.selectAll(getPrincipal().getId());
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void deleteAll(List<CartData> cartDataList) throws ApiException{
        for(CartData cartData:cartDataList){
            delete(cartData.getCartItemId());
        }
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, CartPojo newCartPojo) throws ApiException {
        CartPojo exCartPojo = getCheck(id);
        InventoryPojo inventoryPojo = inventoryService.get(exCartPojo.getProductId());
        if(newCartPojo.getQuantity()>inventoryPojo.getQuantity()){
            throw new ApiException("Item can't be updated as it exceeds the inventory. Present inventory count : "+inventoryPojo.getQuantity());
        }
        exCartPojo.setSellingPrice(newCartPojo.getSellingPrice());
        exCartPojo.setQuantity(newCartPojo.getQuantity());
    }

    @Transactional(rollbackOn  = ApiException.class)
    public CartPojo getCheck(int id) throws ApiException {
            CartPojo cartPojo = cartDao.select(id);
            if(!Objects.nonNull(cartPojo)){
                throw new ApiException("No such item exists in cart with given Id");
            }
            return cartPojo;
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void pushToNewOrder(List<CartData> cartDataList, OrderPojo orderPojo) throws ApiException {
        if(cartDataList.size()==0){
            throw new ApiException("The order can't be created as the cart is empty");
        }
        for(CartData d : cartDataList){
            InventoryPojo a = inventoryService.get(d.getProductId());
            if(d.getQuantity()>a.getQuantity()){
                throw new ApiException("The item "+d.getProductName()+" can't be added to order because sufficient amount not present in inventory. Inventory count = "+a.getQuantity()+"Cart count ="+d.getQuantity());
            }
        }
        int orderId = orderService.addOrder(orderPojo);
        System.out.println(cartDataList.size());
        for(CartData cartData : cartDataList){
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(orderId);
            orderItemPojo.setProductId(cartData.getProductId());
            orderItemPojo.setProductName(cartData.getProductName());
//
//            InventoryPojo inventoryPojo = inventoryService.get(cartData.getProductId());
//            inventoryService.addSub(inventoryPojo,(-1)*(cartData.getQuantity()));
//            System.out.println(cartData.getQuantity());
            orderItemPojo.setQuantity(cartData.getQuantity());
            orderItemPojo.setSellingPrice(cartData.getSellingPrice());
            orderService.addOrderItem(orderItemPojo);
        }

        deleteAll(cartDataList);
    }

}
package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private InventoryService inventoryService;


    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemPojo p) throws ApiException {
            orderItemDao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {
        OrderItemPojo ex = getCheck(id);
        InventoryPojo a = inventoryService.get(ex.getProductId());
        inventoryService.addSub(a,true,ex.getQuantity());
        orderItemDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<OrderItemPojo> getAll() {
        return orderItemDao.selectAll();
    }

    @Transactional
    public List<OrderItemPojo> getAll(int id) {
        return orderItemDao.selectAll(id);
    }


    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, OrderItemPojo p) throws ApiException {
        OrderItemPojo ex = getCheck(id);
        InventoryPojo a = inventoryService.get(ex.getProductId());
        inventoryService.addSub(a,false,p.getQuantity()-ex.getQuantity());
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
    }


    @Transactional
    public OrderItemPojo getCheck(int id) throws ApiException {
            OrderItemPojo p = orderItemDao.select(id);
            if(!Objects.nonNull(p)){
                throw new ApiException("Order item with given id doesn't exist");
            }
        return p;
    }

}
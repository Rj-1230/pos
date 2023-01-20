package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.increff.pos.helper.GetCurrentTime.getCurrentDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public int addOrder(OrderPojo p) throws ApiException {
        return orderDao.insertOrder(p);
    }

    @Transactional
    public List<OrderPojo> getAll() {
        return orderDao.selectAllOrders();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void updateCustomerDetails(int id, OrderPojo p) throws ApiException {
        OrderPojo ex = getCheckOrder(id);
        ex.setCustomerPhone(p.getCustomerPhone());
        ex.setCustomerName(p.getCustomerName());
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void placeOrder(int id) throws ApiException {
        OrderPojo ex = getCheckOrder(id);
        ex.setStatus("invoiced");
        ex.setOrderPlaceTime(getCurrentDateTime());
    }

    @Transactional
    public OrderPojo getCheckOrder(int id) throws ApiException {
        OrderPojo p = orderDao.selectOrder(id);
          if(Objects.isNull(p)){
              throw new ApiException("No order with given id exists");
          }
          return p;
    }

    @Transactional
    public List<OrderPojo> selectOrderByDateFilter(String start, String endDate) throws ApiException {
        List<OrderPojo> orderPojoList = orderDao.selectDateFilter(start, endDate);
        if(Objects.isNull(orderPojoList)){
            throw new ApiException("No orders exists between the given dates");
        }
        return orderPojoList;
    }


    @Transactional(rollbackOn = ApiException.class)
    public void addOrderItem(OrderItemPojo p) throws ApiException {
        InventoryPojo a = inventoryService.get(p.getProductId());
        if(p.getQuantity()>a.getQuantity()){
            throw new ApiException("Item can't be added to order as it exceeds the inventory. Present inventory count : "+a.getQuantity());
        }
        OrderItemPojo b = orderDao.getOrderItemPojoFromProductId(p.getProductId(),p.getOrderId());
        inventoryService.addSub(a,-p.getQuantity());
        if(Objects.nonNull(b)){
            b.setQuantity(b.getQuantity()+p.getQuantity());
        }
        else{
            orderDao.insertOrderItem(p);
        }

    }

    @Transactional(rollbackOn = ApiException.class)
    public void deleteOrderItem(int id) throws ApiException {
        OrderItemPojo ex = getCheckOrderItem(id);
        InventoryPojo a = inventoryService.get(ex.getProductId());
        inventoryService.addSub(a,ex.getQuantity());
        orderDao.deleteOrderItem(id);
    }

    @Transactional
    public List<OrderItemPojo> getAllOrderItems(int id) {
        return orderDao.selectAllOrderItems(id);
    }


    @Transactional(rollbackOn  = ApiException.class)
    public void updateOrderItem(int id, OrderItemPojo p) throws ApiException {
        OrderItemPojo ex = getCheckOrderItem(id);
        InventoryPojo a = inventoryService.get(ex.getProductId());
        inventoryService.addSub(a,-p.getQuantity()+ex.getQuantity());
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
    }


    @Transactional
    public OrderItemPojo getCheckOrderItem(int id) throws ApiException {
        OrderItemPojo p = orderDao.selectOrderItem(id);
        if(!Objects.nonNull(p)){
            throw new ApiException("Order item with given id doesn't exist");
        }
        return p;
    }


}
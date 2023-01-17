package com.increff.pos.service;

//import com.increff.pos.dao.CartDao;
import com.increff.pos.dao.OrderDao;
import com.increff.pos.model.CartData;
import com.increff.pos.model.DateFilterForm;
import com.increff.pos.pojo.CartPojo;
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
    public void deleteOrder(int id) {
        orderDao.deleteOrder(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo getOrderDetails(int id) throws ApiException {
        return getCheckOrder(id);
    }

    @Transactional
    public List<OrderPojo> getAll() {
        return orderDao.selectAllOrders();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void updateOrder(int id, OrderPojo p) throws ApiException {
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
          if(!Objects.nonNull(p)){
              throw new ApiException("No order with given id exists");
          }
          return p;
    }
    @Transactional
    public List<OrderPojo> selectDateFilter(DateFilterForm f) throws ApiException
    {
        String startDate = f.getStart().replace('-', '/')+ " 00:00:00";
        String endDate = f.getEnd().replace('-', '/')+ " 23:59:59";
        return orderDao.selectDateFilter(startDate,endDate);
    }

    @Transactional
    public List<OrderPojo> selectOrderByDateFilter(String start, String endDate) throws ApiException {
        try {
            return orderDao.selectDateFilter(start, endDate);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

}
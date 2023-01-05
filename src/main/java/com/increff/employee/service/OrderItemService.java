package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.InfoData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;
    @Autowired
    private BrandService serviceB;
    @Autowired
    private OrderDao daoO;
    @Autowired
    private OrderService serviceO;
    @Autowired
    private InfoData info;


    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemPojo p) throws ApiException {
        System.out.println("boololooooo");
            dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<OrderItemPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional
    public List<OrderItemPojo> getAll(int id) {
        return dao.selectAll(id);
    }


    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, OrderItemPojo p) throws ApiException {
        normalize(p);
        OrderItemPojo ex = getCheck(id);
        ex.setOrderId(p.getOrderId());
        ex.setQuantity(p.getQuantity());
        ex.setProductName(p.getProductName());
        ex.setSellingPrice(p.getSellingPrice());
        dao.update(p);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, OrderPojo p) throws ApiException {
        normalize(p);
        OrderPojo ex = serviceO.getCheck(id);
        ex.setCustomerName(p.getCustomerName());
        daoO.update(p);
    }

    @Transactional
    public List<OrderItemPojo> get_order(int id)
    {
        return dao.selectAll(id);
    }

    @Transactional
    public OrderItemPojo getCheck(int id) throws ApiException {
        try{
            OrderItemPojo p = dao.select(id);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    protected static void normalize(OrderItemPojo p) {
//        p.setBarcode(p.getBarcode().toLowerCase().trim());

    }
    protected static void normalize(OrderPojo p) {
//        p.setBarcode(p.getBarcode().toLowerCase().trim());
        p.setCustomerName(p.getCustomerName().toLowerCase().trim());

    }
}
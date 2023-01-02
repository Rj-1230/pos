package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.InfoData;
import com.increff.employee.pojo.OrderItemPojo;
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
    private InfoData info;


    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemPojo p) throws ApiException {
        System.out.println("b");
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
        ex.setProductId(p.getProductId());
        ex.setSellingPrice(p.getSellingPrice());
        dao.update(p);
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
}
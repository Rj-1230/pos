package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.model.InfoData;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;
    @Autowired
    private BrandService serviceB;
    @Autowired
    private InfoData info;


    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderPojo p) throws ApiException {
//        normalize(p);
            System.out.println("hello5");
            dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<OrderPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, OrderPojo p) throws ApiException {
        normalize(p);
        OrderPojo ex = getCheck(id);
        dao.update(p);
    }

    @Transactional
    public OrderPojo getCheck(int id) throws ApiException {
        try{
            OrderPojo p = dao.select(id);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    protected static void normalize(OrderPojo p) {
//        p.setBarcode(p.getBarcode().toLowerCase().trim());

    }
}
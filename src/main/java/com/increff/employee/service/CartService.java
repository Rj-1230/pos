package com.increff.employee.service;

import com.increff.employee.dao.CartDao;
import com.increff.employee.model.CartData;
import com.increff.employee.pojo.CartPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(CartPojo p) throws ApiException {
//        System.out.println("b");
            dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
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

    @Transactional
    public void deleteAll(List<CartData> list1) {
        for(CartData d:list1){
            delete(d.getCartItemId());
        }
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, CartPojo p) throws ApiException {
//        normalize(p);
        CartPojo ex = getCheck(id);
//        ex.setCartItemId(p.getCartItemId());
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }

    @Transactional
    public CartPojo getCheck(int id) throws ApiException {
        try{
            CartPojo p = dao.select(id);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

}
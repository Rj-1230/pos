package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Autowired
    private ProductService serviceP;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo p)throws ApiException {
//        normalize(p);

        ProductPojo a = serviceP.get(p.getProductId());
        InventoryPojo b = get(p.getProductId());
        if(a==null) {
            System.out.println("hello3");
            throw new ApiException("The product with given Product Id does not exists !!");
        }
        else if(b!=null){
            throw new ApiException("This product already exists in the inventory !!");
        }
        else{
            System.out.println("hello5");
            dao.insert(p);

        }
    }

    @Transactional
    public void delete (int id){

            dao.delete(id);


    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, InventoryPojo p) throws ApiException {
        InventoryPojo ex = getCheck(id);
        ex.setQuantity(p.getQuantity());
        dao.update(p);
    }

    @Transactional
    public InventoryPojo getCheck(int id) throws ApiException {
        try{
            InventoryPojo p = dao.select(id);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

}
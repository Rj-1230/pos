package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.BrandDao;

@Service
public class BrandService {

    @Autowired
    private BrandDao dao;

    @Transactional
    public void add(BrandPojo p) {
        normalize(p);
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException {
        normalize(p);
        BrandPojo ex = getCheck(id);
        ex.setBrand(p.getBrand());
        ex.setCategory(p.getCategory());
        dao.update(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo getBrandIdPojo(String brandName, String categoryName) throws ApiException {
//        System.out.println(brandName + categoryName);
        return dao.getBrandIdPojo(brandName,categoryName);
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        try{
            BrandPojo p = dao.select(id);
            return p;

        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
//        if (p == null) {
//            return null;
////            throw new ApiException("Employee with given ID does not exit, id: " + id);
//        }
    }

    protected static void normalize(BrandPojo p) {
        p.setBrand(p.getBrand().toLowerCase().trim());
        p.setCategory(p.getCategory().toLowerCase().trim());
    }
}
package com.increff.pos.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.BrandDao;

@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    @Transactional(rollbackOn  = ApiException.class)
    public void add(BrandPojo p) throws ApiException{
        BrandPojo a = brandDao.getBrandPojoFromBrandCategory(p.getBrand(), p.getCategory());
        if(Objects.nonNull(a)){
            throw new ApiException("The given brand-category already exists");
        }
        brandDao.insert(p);
    }
    @Transactional
    public void delete(int id) {
        brandDao.delete(id);
    }
    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    @Transactional
    public List<BrandPojo> getAll() {
        return brandDao.selectAll();
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException {
        BrandPojo a = brandDao.getBrandPojoFromBrandCategory(p.getBrand(), p.getCategory());
        if(Objects.nonNull(a)){
            throw new ApiException("The given brand-category already exists");
        }
        BrandPojo ex = getCheck(id);
        ex.setBrand(p.getBrand());
        ex.setCategory(p.getCategory());
    }

    @Transactional(rollbackOn = ApiException.class)
    public  BrandPojo getCheck(int id) throws ApiException {
        BrandPojo a = brandDao.select(id);
        if(!Objects.nonNull(a)){
            throw new ApiException("No such brand-category with given id exists !");
        }
        return a;
    }

    @Transactional(rollbackOn = ApiException.class)
    public int getBrandIdFromName(String brandName, String categoryName) throws ApiException {
        BrandPojo a = brandDao.getBrandPojoFromBrandCategory(brandName,categoryName);
        if(!Objects.nonNull(a)){
            throw new ApiException("The given brand-category does not exist");
        }
        return a.getId();
    }
}
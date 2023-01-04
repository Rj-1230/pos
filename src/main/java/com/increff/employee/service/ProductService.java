package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.InfoData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;
    @Autowired
    private BrandService serviceB;
    @Autowired
    private InfoData info;


    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        normalize(p);
//
//        BrandPojo a = serviceB.get(p.getBrandId());
//        System.out.println("hello1");

        ProductPojo b = dao.getPojo(p.getBarcode());
//        System.out.println(p.getBarcode());


        if (b!=null){
            System.out.println("hello4");
            throw new ApiException("The product with given Barcode already exists !!");
        }
        else{
            System.out.println("hello5");
            dao.insert(p);

        }
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        ex.setBarcode(p.getBarcode());
        ex.setMrp(p.getMrp());
        ex.setBrandId(p.getBrandId());
        ex.setName(p.getName());
        dao.update(p);
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        try{
            ProductPojo p = dao.select(id);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    protected static void normalize(ProductPojo p) {
        p.setBarcode(p.getBarcode().toLowerCase().trim());
        p.setName(p.getName().toLowerCase().trim());
    }

    public ProductPojo getProductIdFromBarcode(String barcode)throws ApiException{
        try{
            ProductPojo p = dao.getPojo(barcode);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public ProductPojo getBrandIdFromProductId(int id)throws ApiException{
        try{
            ProductPojo p = dao.getPojo(id);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
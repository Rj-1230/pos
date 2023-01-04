package com.increff.employee.dto;

import com.google.protobuf.Api;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.model.ProductUpdateForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service

public class ProductDto {
    @Autowired
    private ProductService service;
    @Autowired
    private BrandService serviceB;

    public void add(ProductForm f) throws ApiException {
        int brandId = getBrandIdFromName(f);
        System.out.println(brandId);
        ProductPojo p = convert(f);
        p.setBrandId(brandId);
        service.add(p);
    }

    public void delete(@PathVariable int id){
        service.delete(id);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = service.get(id);

        return convert(p);
        //before returning , we need to convert our ProductPojo type data into ProductData format
    }

    public void update(@PathVariable int id, @RequestBody ProductUpdateForm f) throws ApiException {
        ProductPojo p = convertUpdate(f);
        int brandId = getBrandIdFromProductId(id);
        System.out.println("Hellooooooooooooo");

        System.out.println(brandId);
        System.out.println("Hellooooooooooooo");

        p.setBrandId(brandId);
        service.update(id,p);
        //before returning , we need to convert our ProductPojo type data into ProductData format
    }

    public List<ProductData> getAll(){
        List<ProductPojo> list = service.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for(ProductPojo p: list){
            list2.add(convert(p));
        }
        return list2;
        //before returning , we need to convert our ProductPojo type data into ProductData format
    }


    private int getBrandIdFromName(ProductForm f) throws ApiException{
//        System.out.println(f.getBrandName() + f.getCategoryName());

        BrandPojo p = serviceB.getBrandIdPojo(f.getBrandName(),f.getCategoryName());
        if(p==null){
            throw new ApiException("The product can't be added as no such brand-category exists !!");
        }
        return p.getId();
    }

    private int getBrandIdFromProductId(int id) throws ApiException{
//        System.out.println(f.getBrandName() + f.getCategoryName());

        ProductPojo p = service.getBrandIdFromProductId(id);
        if(p==null){
            throw new ApiException("The product can't be updated as no such brand-category exists !!");
        }
        return p.getBrandId();
    }

    private static ProductPojo convert(ProductForm f){
        //The convert method will convert JSON format data received into ProductPojo format
        ProductPojo p = new ProductPojo();
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setMrp(f.getMrp());
        return p;
    }

    private static ProductPojo convertUpdate(ProductUpdateForm f){
        //The convert method will convert JSON format data received into ProductPojo format
        ProductPojo p = new ProductPojo();
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setMrp(f.getMrp());
        return p;
    }

    private static ProductData convert(ProductPojo p){
        ProductData d = new ProductData();
        d.setProductId(p.getProductId());
        d.setBarcode(p.getBarcode());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setBrandId(p.getBrandId());
//        data.setMessage("Hola !");
        return d;
    }
}

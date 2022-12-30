package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
//import com.increff.employee.model.ProductData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
@Service

public class BrandDto {
    @Autowired
    private BrandService service;

    public void add(BrandForm f){
        BrandPojo p = convert(f);
        service.add(p);
    }

    public void delete(@PathVariable int id){
        service.delete(id);
    }

    public BrandData get(int id) throws ApiException {
        BrandPojo p = service.get(id);
        return convert(p);
        //before returning , we need to convert our BrandPojo type data into BrandData format
    }

    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        BrandPojo p = convert(f);
        service.update(id,p);
        //before returning , we need to convert our BrandPojo type data into BrandData format
    }

    public List<BrandData> getAll(){
        List<BrandPojo> list = service.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for(BrandPojo p: list){
            list2.add(convert(p));
        }
        return list2;
        //before returning , we need to convert our BrandPojo type data into BrandData format
    }


    private static BrandPojo convert(BrandForm f){
        //The convert method will convert JSON format data received into BrandPojo format
        BrandPojo p = new BrandPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }

    private static BrandData convert(BrandPojo p){
        BrandData d = new BrandData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
//        data.setMessage("Hola !");
        return d;
    }
}

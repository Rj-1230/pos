package com.increff.pos.helper;

import com.increff.pos.model.*;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDtoHelper {

    public static void normalize(ProductForm f) {
        f.setBarcode(f.getBarcode().toLowerCase().trim());
        f.setName(f.getName().toLowerCase().trim());
    }

    public static ProductData convert(ProductPojo p){
        ProductData d = new ProductData();
        d.setProductId(p.getProductId());
        d.setBarcode(p.getBarcode());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setBrandId(p.getBrandId());
//        data.setMessage("Hola !");
        return d;
    }

    public static ProductPojo convert(ProductForm f, int brandId){
        //The convert method will convert JSON format data received into ProductPojo format
        ProductPojo p = new ProductPojo();
        p.setBrandId(brandId);
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setMrp(f.getMrp());
        return p;
    }

    public static ProductPojo convert(ProductForm f){
        //The convert method will convert JSON format data received into ProductPojo format
        ProductPojo p = new ProductPojo();
//        p.setBrandId(brandId);
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setMrp(f.getMrp());
        return p;
    }

    public static List<ProductData> getAllProducts(List<ProductPojo> list){
        List<ProductData> list2 = new ArrayList<ProductData>();
        for(ProductPojo p: list){
            list2.add(convert(p));
        }
        return list2;
    }

}

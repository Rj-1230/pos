package com.increff.pos.helper;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.BrandReportData;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandDtoHelper {
    public static BrandPojo convert(BrandForm f){
        BrandPojo p = new BrandPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }

    public static BrandData convert(BrandPojo p){
        BrandData d = new BrandData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
        return d;
    }

    public static void normalize(BrandForm f) {
        f.setBrand(f.getBrand().toLowerCase().trim());
        f.setCategory(f.getCategory().toLowerCase().trim());
    }

    public static List<BrandData> getAllBrands(List<BrandPojo> list){
        List<BrandData> list2 = new ArrayList<BrandData>();
        for(BrandPojo p: list){
            list2.add(convert(p));
        }
        return list2;
    }

//    public List<BrandReportData> getBrandReportBrandCategory() throws ApiException
//    {
//        List<BrandReportData> list1 = new ArrayList<BrandReportData>();
//
//        List<BrandPojo> brandPojoList = brandService.getAll();
//
////        key: brandId
//        HashMap<Integer, BrandReportData> map = new HashMap<>();
//
//        for(BrandPojo p: brandPojoList) {
//            ProductPojo pp = productService.get(p.getProductId());
//            BrandPojo bp = brandService.get(pp.getBrandId());
//
//            BrandReportData brandReportData = convert(p,bp);
//            if(map.containsKey(bp.getId())){
//                brandReportData.setQuantity(map.get(bp.getId()).getQuantity()+p.getQuantity());
//            }
//            map.put(bp.getId(), brandReportData);
//        }
//        for (Map.Entry<Integer, BrandReportData> e: map.entrySet())
//        {
//            System.out.println(e.getValue().getQuantity());
//            if(e.getValue().getQuantity()==0){
//                continue;
//            }
//            list1.add(e.getValue());
//
//        }
//
//        return list1;
//    }
}

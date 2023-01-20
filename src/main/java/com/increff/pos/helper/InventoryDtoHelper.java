package com.increff.pos.helper;

import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryDtoHelper {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;
    @Autowired
    private InventoryService inventoryService;
    public static InventoryPojo convert(InventoryForm f){
        InventoryPojo p = new InventoryPojo();
        p.setBarcode(f.getBarcode());
        p.setQuantity(f.getQuantity());
        return p;
    }

    public static InventoryData convert(InventoryPojo p){
        InventoryData d = new InventoryData();
        d.setProductId(p.getProductId());
        d.setQuantity(p.getQuantity());
        d.setBarcode(p.getBarcode());
        return d;
    }

    public static InventoryReportData convert(InventoryPojo p, BrandPojo bp) throws ApiException
    {
        InventoryReportData ird = new InventoryReportData();
        ird.setQuantity(p.getQuantity());
        ird.setBrand(bp.getBrand());
        ird.setCategory(bp.getCategory());

        return ird;
    }

    public static void normalize(InventoryForm f) {
        f.setBarcode(f.getBarcode().toLowerCase().trim());
    }


    public static List<InventoryData> getAllInventory(List<InventoryPojo> list){
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for(InventoryPojo p: list){
            list2.add(convert(p));
        }
        return list2;
    }


    public List<InventoryReportData> getInventoryReportBrandCategory() throws ApiException
    {
        List<InventoryReportData> list1 = new ArrayList<InventoryReportData>();

        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();

//        key: brandId
        HashMap<Integer, InventoryReportData> map = new HashMap<>();

        //TODO: Explore Java 8 Streams map reduce
        for(InventoryPojo p: inventoryPojoList) {
            ProductPojo pp = productService.get(p.getProductId());
            BrandPojo bp = brandService.getBrand(pp.getBrandId());

            InventoryReportData inventoryReportData = convert(p,bp);
            if(map.containsKey(bp.getId())){
                inventoryReportData.setQuantity(map.get(bp.getId()).getQuantity()+p.getQuantity());
            }
            map.put(bp.getId(), inventoryReportData);
        }
        for (Map.Entry<Integer, InventoryReportData> e: map.entrySet())
        {
            System.out.println(e.getValue().getQuantity());
            if(e.getValue().getQuantity()==0){
                continue;
            }
            list1.add(e.getValue());

        }

        return list1;
    }
}

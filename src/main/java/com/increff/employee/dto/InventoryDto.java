package com.increff.employee.dto;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryEditForm;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service

public class InventoryDto {
    @Autowired
    private InventoryService service;

    @Autowired
    private ProductService serviceP;

    public void add(InventoryForm f) throws ApiException {

//        ProductPojo pp = serviceP.getProductIdFromBarcode(f.getBarcode());

            InventoryPojo p = convert(f);
//            p.setProductId(pp.getProductId());
            service.add(p);

    }

    public void delete(@PathVariable int id){
        service.delete(id);
    }

    public InventoryData get(int id) throws ApiException {
        InventoryPojo p = service.get(id);
        return convert(p);
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }

    public void addIn(@PathVariable int id, @RequestBody InventoryEditForm f) throws ApiException {
        InventoryPojo p = convert(f);
        service.addIn(id,p);
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }

    public void subIn(@PathVariable int id, @RequestBody InventoryEditForm f) throws ApiException {
        InventoryPojo p = convert(f);
        service.subIn(id,p);
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }
    public List<InventoryData> getAll(){
        List<InventoryPojo> list = service.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for(InventoryPojo p: list){
            list2.add(convert(p));
        }
        return list2;
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }

    private static InventoryPojo convert(InventoryForm f){
        //The convert method will convert JSON format data received into InventoryPojo format
        InventoryPojo p = new InventoryPojo();
        p.setBarcode(f.getBarcode());
//        p.setProductId(f.getProductId());
        p.setQuantity(f.getQuantity());
        return p;
    }

    private static InventoryPojo convert(InventoryEditForm f){
        //The convert method will convert JSON format data received into InventoryPojo format
        InventoryPojo p = new InventoryPojo();
//        p.setBarcode(f.getBarcode());
//        p.setProductId(f.getProductId());
        p.setQuantity(f.getQuantity());
        return p;
    }
    private static InventoryData convert(InventoryPojo p){
        InventoryData d = new InventoryData();
        d.setProductId(p.getProductId());
        d.setQuantity(p.getQuantity());
        d.setBarcode(p.getBarcode());
//        data.setMessage("Hola !");
        return d;
    }
}

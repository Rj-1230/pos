package com.increff.pos.dto;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryEditForm;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.helper.InventoryDtoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.increff.pos.helper.InventoryDtoHelper.*;
import static com.increff.pos.helper.NullCheckHelper.checkNullable;

@Service

public class InventoryDto {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryDtoHelper inventoryDtoHelper;

//    public void add(InventoryForm f) throws ApiException {
//            checkNullable(f);
//            normalize(f);
//            InventoryPojo p = convert(f);
//            inventoryService.add(p);
//    }
    public void addSub(InventoryForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        InventoryPojo p = convert(f);
        boolean x = true;
        inventoryService.addSub(p,x, p.getQuantity());
    }

    public void delete(@PathVariable int id){
        inventoryService.delete(id);
    }

    public InventoryData get(int id) throws ApiException {
        InventoryPojo p = inventoryService.get(id);
        return convert(p);
    }

//    public void addIn(@PathVariable int id, @RequestBody InventoryEditForm f) throws ApiException {
//        InventoryPojo p = convert(f);
//        inventoryService.addIn(id,p);
//    }
//
//    public void subIn(@PathVariable int id, @RequestBody InventoryEditForm f) throws ApiException {
//        InventoryPojo p = convert(f);
//        inventoryService.subIn(id, p);
//    }
    public List<InventoryData> getAll(){
        return getAllInventory(inventoryService.getAll());
    }

    public List<InventoryReportData> getInventoryReport() throws ApiException
    {
        return inventoryDtoHelper.getInventoryReportBrandCategory();
    }

}

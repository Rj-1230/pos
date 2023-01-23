package com.increff.pos.dto;

import com.increff.pos.flow.InventoryFlow;
import com.increff.pos.model.InventoryData;
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
    private InventoryFlow inventoryFlow;
    @Autowired
    private InventoryDtoHelper inventoryDtoHelper;

    public void addSub(InventoryForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        InventoryPojo inventoryPojo = convert(f);
        inventoryFlow.addSub(inventoryPojo,inventoryPojo.getQuantity());
    }

    public void delete(@PathVariable int id){
        inventoryService.delete(id);
    }

    public InventoryData get(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.getCheck(id);
        return convert(inventoryPojo);
    }

    public List<InventoryData> getAll(){
        return getAllInventory(inventoryService.getAll());
    }

    public List<InventoryReportData> getInventoryReport() throws ApiException
    {
        return inventoryDtoHelper.getInventoryReportBrandCategory();
    }

}

package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api

public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value="Deleting a product from Inventory")
    @RequestMapping(path="/api/inventory/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException{
        inventoryDto.delete(id);
    }

    @ApiOperation(value="Getting details of a product in the Inventory")
    @RequestMapping(path="/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
       return inventoryDto.get(id);
    }

    @ApiOperation(value="Getting details of all the Inventory items")
    @RequestMapping(path="/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll(){
        return inventoryDto.getAll();
    }

    @ApiOperation(value = "Get inventory report of product items with brand-category")
    @RequestMapping(path = "/api/inventoryReport", method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryReport() throws ApiException
    {
        return inventoryDto.getInventoryReport();
    }

    @ApiOperation(value="Adding or removing a product from the inventory")
    @RequestMapping(path="/api/inventoryAddSub", method = RequestMethod.POST)
    public void addSub(@RequestBody InventoryForm f) throws ApiException{
        inventoryDto.addSub(f);
    }
}

package com.increff.employee.controller;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.InventoryData;
//import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api

public class InventoryController {

    @Autowired
    private InventoryDto dto;

    @ApiOperation(value="Adding a product in the inventory")
    @RequestMapping(path="/api/inventory", method = RequestMethod.POST)
    public String add(@RequestBody InventoryForm f) throws ApiException{

        String message = "Product added successfully in the inventory";
        try {
            dto.add(f);
        }
        catch (Exception e)
        {

            message = e.getMessage();
            System.out.println("Exception message : " + message);
        }

        return message;
    }

    @ApiOperation(value="Deleting a product from Inventory")
    @RequestMapping(path="/api/inventory/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException{
        dto.delete(id);
    }

    @ApiOperation(value="Getting details of a product in the Inventory")
    @RequestMapping(path="/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
       return dto.get(id);
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }

    @ApiOperation(value="Adding items of a particular product in the inventory")
    @RequestMapping(path="/api/inventoryAdd/{id}", method = RequestMethod.PUT)
    public void addToInventory(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        dto.addIn(id,f);
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }

    @ApiOperation(value="Removing items of a particular product in the inventory")
    @RequestMapping(path="/api/inventorySub/{id}", method = RequestMethod.PUT)
    public void removeFromInventory(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        dto.subIn(id,f);
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }

    @ApiOperation(value="Getting details of all the Inventory items")
    @RequestMapping(path="/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll(){
        return dto.getAll();
        //before returning , we need to convert our InventoryPojo type data into InventoryData format
    }

}

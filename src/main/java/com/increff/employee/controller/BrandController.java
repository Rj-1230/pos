package com.increff.employee.controller;

import com.increff.employee.dto.BrandDto;
import com.increff.employee.model.BrandData;
//import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
//import com.increff.employee.dto.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api

public class BrandController {
    @Autowired
    private BrandDto dto;

    @ApiOperation(value="Adding a brand")
    @RequestMapping(path="/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm f){
//        BrandPojo p = convert(BrandForm);
        dto.add(f);
    }

    @ApiOperation(value="Deleting a brand")
    @RequestMapping(path="/api/brand/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        dto.delete(id);
    }

    @ApiOperation(value="Getting details of a brand")
    @RequestMapping(path="/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
        //before returning , we need to convert our BrandPojo type data into BrandData format
    }

    @ApiOperation(value="Updating details of a particular brand-category combo")
    @RequestMapping(path="/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        dto.update(id,f);
        //before returning , we need to convert our BrandPojo type data into BrandData format
    }

    @ApiOperation(value="Getting details of all the brand-category")
    @RequestMapping(path="/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return dto.getAll();
    }
}

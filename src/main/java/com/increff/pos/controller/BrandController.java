package com.increff.pos.controller;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.Forbidden;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.increff.pos.util.SecurityUtil.getAuthentication;

@RestController
@Api

public class BrandController {
    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value="Adding a brand")
    @RequestMapping(path="/api/admin/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm f) throws ApiException, Forbidden {
        Authentication auth = getAuthentication();
            brandDto.add(f);
    }

    //    Although delete is disabled from UI, but method made for future use
    @ApiOperation(value="Deleting a brand")
    @RequestMapping(path="/api/brand/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        brandDto.delete(id);
    }

    @ApiOperation(value="Getting details of a brand from brandId")
    @RequestMapping(path="/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value="Updating details of a particular brand-category combo")
    @RequestMapping(path="/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        brandDto.update(id,f);
    }

    @ApiOperation(value="Getting details of all the brand-category")
    @RequestMapping(path="/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandDto.getAll();
    }
}

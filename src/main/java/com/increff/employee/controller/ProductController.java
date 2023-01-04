package com.increff.employee.controller;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.model.ProductUpdateForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api

public class ProductController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value="Adding a product")
    @RequestMapping(path="/api/product", method = RequestMethod.POST)
    public String add(@RequestBody ProductForm f)throws ApiException{
        String message = "Product added successfully";
        try {
            System.out.println(f.getBrandName()+f.getCategoryName()+f.getName()+f.getBarcode()+f.getMrp());
//            ProductPojo p = convert(productForm);
            dto.add(f);
        }
        catch (Exception e)
        {
            System.out.println("This is exception message" + e);
            message = e.getMessage();
        }

        return message;
    }



    @ApiOperation(value="Deleting a product")
    @RequestMapping(path="/api/product/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        dto.delete(id);
    }

    @ApiOperation(value="Getting details of a product")
    @RequestMapping(path="/api/product/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
        //before returning , we need to convert our ProductPojo type data into ProductData format
    }

    @ApiOperation(value="Updating details of a particular Product")
    @RequestMapping(path="/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductUpdateForm f) throws ApiException {
        dto.update(id,f);
        //before returning , we need to convert our ProductPojo type data into ProductData format
    }

    @ApiOperation(value="Getting details of all the products")
    @RequestMapping(path="/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll(){
        return dto.getAll();
        //before returning , we need to convert our ProductPojo type data into ProductData format
    }

}

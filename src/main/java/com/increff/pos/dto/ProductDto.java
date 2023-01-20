package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import static com.increff.pos.helper.NullCheckHelper.*;
import static com.increff.pos.helper.ProductDtoHelper.*;

import java.util.List;

@Service

public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;


    public void add(ProductForm f) throws ApiException {
        checkNullable(f);
        int brandId = brandService.getBrandIdFromName(f.getBrandName(),f.getCategoryName());
        normalize(f);
        ProductPojo p = convert(f,brandId);
        productService.add(p);
    }

    public void delete(@PathVariable int id){
        productService.delete(id);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = productService.get(id);
        return convert(p);
    }

    public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        ProductPojo p = convert(f);
        productService.update(id,p);
    }

    public List<ProductData> getAll(){
        return getAllProducts(productService.getAll());
    }
}
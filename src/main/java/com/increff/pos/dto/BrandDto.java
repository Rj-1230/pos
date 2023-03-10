package com.increff.pos.dto;

import com.increff.pos.helper.BrandDtoHelper;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.BrandReportData;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.increff.pos.helper.BrandDtoHelper.*;
import static com.increff.pos.helper.NullCheckHelper.*;

@Service

public class BrandDto {
    @Autowired
    private BrandService brandService;

    public void addBrand(BrandForm f)throws  ApiException{
        checkNullable(f);
        normalize(f);
        BrandPojo brandPojo = convert(f);
        brandService.addBrand(brandPojo);
    }
    public void deleteBrand(@PathVariable int id){
        brandService.deleteBrand(id);
    }

    public BrandData getBrand(int id) throws ApiException {
        BrandPojo brandPojo = brandService.getCheckBrand(id);
        return convert(brandPojo);
    }
    public void updateBrand(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        BrandPojo brandPojo = convert(f);
        brandService.updateBrand(id,brandPojo);
    }
    public List<BrandData> getAll(){
        return getAllBrands(brandService.getAll());
    }

}

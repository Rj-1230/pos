package com.increff.pos.dto;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
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

    public void add(BrandForm f)throws  ApiException{
        checkNullable(f);
        normalize(f);
        BrandPojo p = convert(f);
        brandService.add(p);
    }
    public void delete(@PathVariable int id){
        brandService.delete(id);
    }

    public BrandData get(int id) throws ApiException {
        BrandPojo p = brandService.get(id);
        return convert(p);
    }
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        BrandPojo p = convert(f);
        brandService.update(id,p);
    }
    public List<BrandData> getAll(){
        return getAllBrands(brandService.getAll());
    }

}
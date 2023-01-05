package com.increff.employee.dto;

import com.increff.employee.model.BrandRevenueData;
import com.increff.employee.model.CategoryRevenueData;
import com.increff.employee.model.DateFilterForm;
import com.increff.employee.model.ProductRevenueData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RevenueDto {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    public List<ProductRevenueData> get_revenue_product(DateFilterForm form) throws ApiException
    {
        List<ProductRevenueData> list1 = new ArrayList<ProductRevenueData>();

        List<ProductPojo> productPojoList = productService.getAll();

//        key: productId
        HashMap<Integer, ProductRevenueData> map = new HashMap<>();

//        getting the list of all available products in map
        for(ProductPojo p: productPojoList) {
            ProductRevenueData productRevenueData = convert(p);
            map.put(p.getProductId(), productRevenueData);
        }
//      converting the date into required formate
        String startDate = form.getStart().replace('-', '/');
        String endDate = form.getEnd().replace('-', '/');
        startDate += " 00:00:00";
        endDate += " 24:00:00";

        List<OrderPojo> orderPojoList = orderService.selectDateFilter(startDate, endDate);

//        setting the quantity and total
//          -> order wise filtered on basis of date
        for(OrderPojo e: orderPojoList)
        {
            int orderId = e.getOrderId();
            List<OrderItemPojo> orderItemPojoList = orderItemService.get_order(orderId);

            for(OrderItemPojo p: orderItemPojoList)
            {
                int productId = p.getProductId();
                int quantity = map.get(productId).getQuantity();
                double total = map.get(productId).getTotal();

                map.get(productId).setQuantity(quantity + p.getQuantity());
                map.get(productId).setTotal(total + p.getQuantity()*p.getSellingPrice());
            }
        }

//        Converting map to list
        for (Map.Entry<Integer, ProductRevenueData> e: map.entrySet())
            list1.add(e.getValue());

        return list1;
    }

    public List<BrandRevenueData> get_revenue_brand(DateFilterForm form) throws ApiException {
        List<BrandRevenueData> res = new ArrayList<>();

//        key: brand name
        HashMap<String, BrandRevenueData> map = new HashMap<>();

        List<ProductRevenueData> list1 = get_revenue_product(form);

        for(ProductRevenueData p: list1)
        {
            if(map.containsKey(p.getBrand()))
            {
                int quantity = map.get(p.getBrand()).getQuantity();
                double total = map.get(p.getBrand()).getTotalBrandRevenue();

                map.get(p.getBrand()).setQuantity(quantity + p.getQuantity());
                map.get(p.getBrand()).setTotalBrandRevenue(total + p.getTotal());
            }
            else{
                BrandRevenueData b = new BrandRevenueData();
                b.setBrand(p.getBrand());
                b.setTotalBrandRevenue(p.getTotal());
                b.setQuantity(p.getQuantity());

                map.put(p.getBrand(), b);
            }
        }

        for(Map.Entry<String, BrandRevenueData> e: map.entrySet())
            res.add(e.getValue());

        return res;
    }

    public List<CategoryRevenueData> get_revenue_category(DateFilterForm form) throws ApiException {
        List<CategoryRevenueData> res = new ArrayList<>();

        HashMap<String, CategoryRevenueData> map = new HashMap<>();

        List<ProductRevenueData> list1 = get_revenue_product(form);

        for(ProductRevenueData p: list1)
        {
            if(map.containsKey(p.getCategory()))
            {
                String key = p.getCategory();
                int quantity = map.get(key).getQuantity();
                double total = map.get(key).getTotalCategoryRevenue();

                map.get(key).setQuantity(quantity + p.getQuantity());
                map.get(key).setTotalCategoryRevenue(total + p.getTotal());
            }
            else{
                CategoryRevenueData b = new CategoryRevenueData();
                b.setCategory(p.getCategory());
                b.setTotalCategoryRevenue(p.getTotal());
                b.setQuantity(p.getQuantity());

                map.put(p.getCategory(), b);
            }
        }

        for(Map.Entry<String, CategoryRevenueData> e: map.entrySet())
            res.add(e.getValue());


        return res;
    }

    private ProductRevenueData convert(ProductPojo p) throws ApiException
    {
        ProductRevenueData productRevenueData = new ProductRevenueData();
        productRevenueData.setId(p.getProductId());
        productRevenueData.setBarcode(p.getBarcode());
        productRevenueData.setMrp(p.getMrp());
        productRevenueData.setName(p.getName());
        productRevenueData.setQuantity(0);
        productRevenueData.setTotal(0);

        int brandCategoryId = p.getBrandId();
        BrandPojo brandPojo = brandService.get(brandCategoryId);

        productRevenueData.setBrand(brandPojo.getBrand());
        productRevenueData.setCategory(brandPojo.getCategory());

        return productRevenueData;
    }
}
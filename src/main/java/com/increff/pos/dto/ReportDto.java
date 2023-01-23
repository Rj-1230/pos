package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.*;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.pos.helper.ReportDtoHelper.convert;
import static com.increff.pos.helper.ReportDtoHelper.filterByBrandCategory;
import static com.increff.pos.util.GetCurrentDateTime.getLocalDate;

@Service
public class ReportDto {
    @Autowired
    ReportService reportService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;
    @Autowired
    InventoryService inventoryService;




    public List<ProductRevenueData> getRevenueBrandCategoryWise(DateBrandCategoryFilterForm form) throws ApiException {
        List<ProductRevenueData> list1 = new ArrayList<ProductRevenueData>();

        List<ProductPojo> productPojoList = productService.getAll();
        HashMap<Integer, ProductRevenueData> map = new HashMap<>();
        for(ProductPojo p: productPojoList) {
            BrandPojo brandPojo = brandService.getCheckBrand(p.getBrandId());
            ProductRevenueData productRevenueData = convert(p,brandPojo);
            map.put(p.getProductId(), productRevenueData);
        }
////      converting the date into required format
        String startDate = form.getStart() + " 00:00:00";
        String endDate = form.getEnd() + " 23:59:59";

        List<OrderPojo> orderPojoList = orderService.selectOrderByDateFilter(startDate,endDate);
        for(OrderPojo e: orderPojoList)
        {
            int orderId = e.getOrderId();
            List<OrderItemPojo> orderItemPojoList = orderService.getAllOrderItems(orderId);
            for(OrderItemPojo p: orderItemPojoList)
            {
                int productId = p.getProductId();
                int quantity = map.get(productId).getQuantity();
                if(p.getQuantity()==0){
                    continue;
                }

                double total = map.get(productId).getTotal();
                map.get(productId).setQuantity(quantity + p.getQuantity());
                map.get(productId).setTotal(total + p.getQuantity()*p.getSellingPrice());
            }
        }

//        Converting map to list
        for (Map.Entry<Integer, ProductRevenueData> e: map.entrySet())
        {
            if(e.getValue().getQuantity()==0)
                continue;
            if(filterByBrandCategory(e.getValue().getBrand(),e.getValue().getCategory(),form.getBrand(),form.getCategory()))
                list1.add(e.getValue());
        }
        return list1;
    }




    public List<InventoryReportData> getInventoryBrandCategoryWise(BrandForm form) throws ApiException {
        List<InventoryReportData> list1 = new ArrayList<InventoryReportData>();

        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        HashMap<Integer, InventoryReportData> map = new HashMap<>();

//        getting the list of all available products in map
        for(InventoryPojo p: inventoryPojoList) {
            ProductPojo productPojo = productService.getCheck(p.getProductId());
            BrandPojo brandPojo = brandService.getCheckBrand(productPojo.getBrandId());
            InventoryReportData inventoryReportData = convert(p,brandPojo);
            map.put(p.getProductId(), inventoryReportData);
        }
//        Converting map to list
        for (Map.Entry<Integer, InventoryReportData> e: map.entrySet())
        {
            if(filterByBrandCategory(e.getValue().getBrand(),e.getValue().getCategory(),form.getBrand(),form.getCategory()))
                list1.add(e.getValue());
        }

        return list1;
    }

    public List<BrandData> getBrandReport(BrandForm form) throws ApiException {
        List<BrandData> list1 = new ArrayList<BrandData>();

        List<BrandPojo> brandPojoList = brandService.getAll();
        HashMap<Integer, BrandData> map = new HashMap<>();
        for(BrandPojo p: brandPojoList) {
            BrandData brandData = convert(p);
            map.put(p.getId(), brandData);
        }
        for (Map.Entry<Integer,BrandData> e: map.entrySet())
        {
            if(filterByBrandCategory(e.getValue().getBrand(),e.getValue().getCategory(),form.getBrand(),form.getCategory()))
                list1.add(e.getValue());
        }
        return list1;
    }



    public List<DailyReportData> getDailySalesFilteredReport(DateFilterForm form) throws ApiException
    {
        List<DailyReportData> list1 = new ArrayList<DailyReportData>();
        HashMap<LocalDate, DailyReportData> map = new HashMap<>();
        LocalDate startDate = LocalDate.parse(form.getStart());
        LocalDate endDate = LocalDate.parse(form.getEnd());
        List<DailyReportPojo> dailyReportPojoList = reportService.selectReportByDateFilter(startDate,endDate);
        for(DailyReportPojo p: dailyReportPojoList) {
            DailyReportData dailyReportData = convert(p);
            map.put(p.getDate(),dailyReportData);
        }

        for (Map.Entry<LocalDate, DailyReportData> e: map.entrySet())
        {
            list1.add(e.getValue());

        }

        return list1;
    }



    public void createDailyReport() throws ApiException {
        System.out.println("cReating report");
        DailyReportPojo reportPojo = new DailyReportPojo();
        LocalDate date = getLocalDate();

        Integer totalItems = 0;
        Double totalRevenue = 0.0;
        String startDate = date + " 00:00:00";
        String endDate = date + " 23:59:59";
        List<OrderPojo> orderPojoList = orderService.selectOrderByDateFilter(startDate, endDate);
        Integer totalOrders = orderPojoList.size();
        for (OrderPojo o : orderPojoList) {
            Integer id = o.getOrderId();
            List<OrderItemPojo> orderItemPojoList = orderService.getAllOrderItems(id);
            for (OrderItemPojo i : orderItemPojoList) {
                totalItems += i.getQuantity();
                totalRevenue += i.getQuantity() * i.getSellingPrice();
            }
        }

        reportPojo.setDate(date);
        reportPojo.setTotalRevenue(totalRevenue);
        reportPojo.setInvoicedItemsCount(totalItems);
        reportPojo.setInvoicedOrderCount(totalOrders);

        DailyReportPojo pojo = reportService.getReportByDate(date);
        if (pojo == null) {
            reportService.addReport(reportPojo);
        } else {
            reportService.update(date, reportPojo);
        }
    }


}
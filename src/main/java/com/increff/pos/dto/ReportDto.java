package com.increff.pos.dto;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.DailyReportPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.increff.pos.util.GetCurrentDateTime.getLocalDate;

@Service
public class ReportDto {
    @Autowired
    ReportService service;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    public void createDailyReport() throws ApiException {
        DailyReportPojo reportPojo = new DailyReportPojo();

        LocalDate date = getLocalDate();

        Integer totalItems = 0;
        Double totalRevenue = 0.0;

//        String startDate = correctFormat(date.toString()) + " 00:00:00";
//        String endDate = correctFormat(date.toString()) + " 23:59:59";

        String startDate = date + " 00:00:00";
        String endDate = date + " 23:59:59";
        System.out.println(startDate+"   "+endDate);
        List<OrderPojo> orderPojoList = orderService.selectOrderByDateFilter(startDate, endDate);
        Integer totalOrders = orderPojoList.size();
        for (OrderPojo o : orderPojoList) {
            Integer id = o.getOrderId();
            List<OrderItemPojo> orderItemPojoList = orderItemService.getAll(id);
            for (OrderItemPojo i : orderItemPojoList) {
                totalItems += i.getQuantity();
                totalRevenue += i.getQuantity() * i.getSellingPrice();
            }
        }

        reportPojo.setDate(date);
        reportPojo.setTotalRevenue(totalRevenue);
        reportPojo.setInvoicedItemsCount(totalItems);
        reportPojo.setInvoicedOrderCount(totalOrders);

        DailyReportPojo pojo = service.getReportByDate(date);
        if (pojo == null) {
            service.addReport(reportPojo);
        } else {
            service.update(date, reportPojo);
        }
    }


    public List<DailyReportPojo> getAllReport() throws ApiException {
        return service.getAllReport();
    }

//    String correctFormat(String date) {
//        return date.replace('-', '/');
//    }
}
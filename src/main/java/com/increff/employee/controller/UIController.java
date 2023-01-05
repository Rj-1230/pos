package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.InfoData;

@Controller
public class UIController {

    @Autowired
    OrderDto dto;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @RequestMapping(value = "")
    public String index() {
        return "index.html";
    }

    @RequestMapping(value = "/ui/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/ui/brands")
    public ModelAndView brands() {
        return mav("brands.html");
    }

    @RequestMapping(value = "/ui/products")
    public ModelAndView products() {
        return mav("products.html");
    }
    @RequestMapping(value = "/ui/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/ui/orders")
    public ModelAndView orders() {
        return mav("orders.html");
    }

    @RequestMapping(value = "/ui/orderItems")
    public ModelAndView orderItem() {
        return mav("orderItems.html");
    }

    @RequestMapping(value = "/ui/orderItem/{id}")
    public ModelAndView orderItem(@PathVariable int id) throws ApiException {
        OrderData data = dto.get(id);
        return mav("orderItem.html", data);
    }

    @RequestMapping(value = "/ui/cart")
    public ModelAndView cartItems() {
        return mav("cart.html");
    }

    private ModelAndView mav(String page) {
        ModelAndView mav = new ModelAndView(page);
        mav.addObject("info", new InfoData());
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

    private ModelAndView mav(String page, OrderData d) {
        ModelAndView mav = new ModelAndView(page);
        mav.addObject("info", new InfoData());
        mav.addObject("orderId", d.getOrderId());
        mav.addObject("customerName", d.getCustomerName());
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

}
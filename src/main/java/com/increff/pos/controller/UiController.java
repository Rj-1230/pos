package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.OrderData;
import com.increff.pos.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UiController extends AbstractUiController{

    @Autowired
    OrderDto dto;


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

    @RequestMapping(value = "/ui/revenue")
    public ModelAndView revenue() {
        return mav("revenue.html");
    }

    @RequestMapping(value = "/ui/inventoryReport")
    public ModelAndView inventoryReport() {
        return mav("inventoryReport.html");
    }

    @RequestMapping(value = "/ui/orderItem/{id}")
    public ModelAndView orderItem(@PathVariable int id) throws ApiException {
        OrderData data = dto.getOrderDetails(id);
        return mav("orderItem.html", data);
    }

    @RequestMapping(value = "/ui/cart")
    public ModelAndView cartItems() {
        return mav("cart.html");
    }



}
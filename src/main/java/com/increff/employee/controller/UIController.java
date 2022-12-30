package com.increff.employee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.InfoData;

@Controller
public class UIController {

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
    private ModelAndView mav(String page) {
        ModelAndView mav = new ModelAndView(page);
        int a=10;
        mav.addObject("info", new InfoData(a));
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

}
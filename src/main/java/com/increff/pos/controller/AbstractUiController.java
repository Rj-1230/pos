package com.increff.pos.controller;

import com.increff.pos.model.InfoData;
import com.increff.pos.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class AbstractUiController {

	@Autowired
	private InfoData info;

	@Value("${app.baseUrl}")
	private String baseUrl;

	protected ModelAndView mav(String page) {
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", new InfoData());
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

	protected ModelAndView mav(String page, OrderData d) {
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", new InfoData());
		mav.addObject("orderId", d.getOrderId());
		mav.addObject("customerName", d.getCustomerName());
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}


}

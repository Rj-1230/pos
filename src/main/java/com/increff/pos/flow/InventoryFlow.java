package com.increff.pos.flow;

import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Objects;

public class InventoryFlow {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public void addSub(InventoryPojo newInventoryPojo, int quantity) throws ApiException {
        ProductPojo productPojo = productService.getProductPojoFromBarcode(newInventoryPojo.getBarcode());
        newInventoryPojo.setProductId(productPojo.getProductId());
        inventoryService.addSub(newInventoryPojo,quantity);
    }
}

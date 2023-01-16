package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.increff.pos.helper.NullCheckHelper.*;
import static com.increff.pos.helper.OrderItemDtoHelper.*;
@Service

public class OrderItemDto {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;


    public void add(OrderItemForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        ProductPojo p= productService.getProductPojoFromBarcode(f.getBarcode());
        OrderItemPojo o = convert(f,p);
        orderItemService.add(o);
    }

    public void delete(@PathVariable int id) throws ApiException {
        orderItemService.delete(id);
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo p = orderItemService.get(id);
        return convert(p);
    }

    public void update(@PathVariable int id, @RequestBody CartEditForm f) throws ApiException {
        OrderItemPojo o = convert(f);
        orderItemService.update(id,o);
    }

    public List<OrderItemData> getAll(){
        return getAllOrderItems(orderItemService.getAll());
    }

    public List<OrderItemData> getAll(int orderId){
        return getAllOrderItemsOfAgivenOrder(orderItemService.getAll(orderId));
    }
}

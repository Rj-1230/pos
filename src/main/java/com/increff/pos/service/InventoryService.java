package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;


    @Transactional(rollbackOn = ApiException.class)
    public void addSub(InventoryPojo newInventoryPojo, int quantity) throws ApiException {
        InventoryPojo exInventoryPojo = getCheck(newInventoryPojo.getProductId());
        if (Objects.nonNull(exInventoryPojo)) {
            if (exInventoryPojo.getQuantity() + quantity < 0) {
                throw new ApiException("There is not sufficient quantity in the inventory for the item");
            }
            exInventoryPojo.setQuantity(exInventoryPojo.getQuantity() + quantity);
        } else {
            inventoryDao.insert(newInventoryPojo);
        }
    }


    @Transactional
    public void delete(int id) {
        inventoryDao.delete(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return inventoryDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo getCheck(int id) throws ApiException {

            InventoryPojo inventoryPojo = inventoryDao.select(id);
        if(!Objects.nonNull(inventoryPojo)){
            throw new ApiException("No such brand-category with given id exists !");
        }
        return inventoryPojo;
    }

}
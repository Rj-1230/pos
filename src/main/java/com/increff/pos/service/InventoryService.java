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
    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public void addSub(InventoryPojo newP, boolean x,int quantity)throws ApiException {
        ProductPojo a = productService.getProductPojoFromBarcode(newP.getBarcode());
        newP.setProductId(a.getProductId());
        InventoryPojo exP = get(newP.getProductId());
        if(Objects.nonNull(exP)){
            if(x){
                exP.setQuantity(exP.getQuantity()+quantity);
            }
            else{
                if(exP.getQuantity()-quantity <0){
                    throw new ApiException("There is not sufficient quantity in the inventory for the item");
                }
                exP.setQuantity(exP.getQuantity()-quantity);
            }
        }
        else{
            inventoryDao.insert(newP);
        }
    }


    @Transactional
    public void delete (int id){
            inventoryDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return inventoryDao.selectAll();
    }
    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo getCheck(int id) throws ApiException {
        try{
            InventoryPojo p = inventoryDao.select(id);
            return p;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

//    @Transactional(rollbackOn  = ApiException.class)
//    public void subFromInventory(int id, int quantity) throws ApiException {
//        InventoryPojo ex = getCheck(id);
//        if(ex.getQuantity()-quantity <0){
//            throw new ApiException("The product can't be added to order as there is not sufficient quantity in the inventory");
//        }
//        ex.setQuantity(ex.getQuantity()-quantity);
//    }

}
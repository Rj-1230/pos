package com.increff.pos.helper;

import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;

import java.util.Objects;

public class NullCheckHelper {
    public static void checkNullable(BrandForm f) throws ApiException {
        if(Objects.equals(f.getBrand(), "")){
            throw new ApiException("The brand name field can't be empty");
        }
        if(Objects.equals(f.getCategory(), "")){
            throw new ApiException("The category name field can't be empty");
        }
    }

    public static void checkNullable(ProductForm f) throws ApiException {
        if(Objects.equals(f.getBarcode(), "")){
            throw new ApiException("The barcode can't be empty");
        }
        if(Objects.equals(f.getName(), "")){
            throw new ApiException("The product name field can't be empty");
        }

        if(f.getMrp()<=0.0d){
            throw new ApiException("The MRP of product can't be 0 or negative");
        }

    }


    public static void checkNullable(InventoryForm f) throws ApiException {
        if(Objects.equals(f.getBarcode(), "")){
            throw new ApiException("The barcode can't be empty");
        }
        if(f.getQuantity()==0){
            throw new ApiException("The item quantity to be added in the inventory can't be 0");
        }
    }

    public static void checkNullable(CartForm f) throws ApiException {
        if(Objects.equals(f.getBarcode(),"")){
            throw new ApiException("The barcode can't be null");
        }
        if(f.getQuantity()==0){
            throw new ApiException("The item quantity to be added in the cart can't be 0");
        }
    }

    public static void checkNullable(OrderForm f) throws ApiException {
        if(Objects.equals(f.getCustomerName(),"")){
            throw new ApiException("The customer name can't be empty");
        }
        if(Objects.equals(f.getCustomerPhone(),"")){
            throw new ApiException("The customer phone number must contain 10 digits");
        }
        if (!onlyDigits(f.getCustomerPhone(),f.getCustomerPhone().length())){
            throw new ApiException("The customer phone must be a valid 10 digit number");
        }
    }

    public static boolean onlyDigits(String str, int n)
    {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static void checkNullable(OrderItemForm f) throws ApiException {
        if(Objects.equals(f.getBarcode(),"")){
            throw new ApiException("The barcode can't be empty");
        }
        if(f.getQuantity()==0){
            throw new ApiException("The items to be added to order can't be 0");
        }
    }


    public static void checkNullable(UserForm f) throws ApiException {
        if(Objects.equals(f.getEmail(), "")){
            throw new ApiException("Email can't be empty");
        }
        if(Objects.equals(f.getPassword(), "")){
            throw new ApiException("The password can't be empty");
        }
    }
}

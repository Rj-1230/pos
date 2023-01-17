package com.increff.pos.helper;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.UserPojo;

import java.util.ArrayList;
import java.util.List;

public class UserDtoHelper {
    public static UserData convertUserPojoToUserData(UserPojo p) {
        UserData d = new UserData();
        d.setEmail(p.getEmail());
        d.setPassword(p.getPassword());
//        d.setRole(p.getRole());
        d.setId(p.getId());
        return d;
    }

    public static UserPojo convertUserFormToUserPojo(UserForm f) {
        UserPojo p = new UserPojo();
        p.setEmail(f.getEmail());
//        p.setRole(f.getRole());
        p.setPassword(f.getPassword());
        return p;
    }

    public static void normalize(UserForm f) {
        f.setEmail(f.getEmail().toLowerCase().trim());
        f.setPassword(f.getPassword().trim());
//        f.setRole(f.getRole().toLowerCase().trim());
//        password ko lowercase me krne ki jrurrat to ni h

    }

    public static List<UserData> getAllUserData(List<UserPojo> list){
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            list2.add(convertUserPojoToUserData(p));
        }
        return list2;
    }


}

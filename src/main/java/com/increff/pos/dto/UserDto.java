package com.increff.pos.dto;

import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.UserDtoHelper.*;
@Service
public class UserDto {
    @Autowired
    UserService service;

    public void addUser(UserForm form) throws ApiException
    {
        UserPojo p = convertUserFormToUserPojo(form);
        service.add(p);
    }

    public void deleteUser(Integer id)
    {
        service.delete(id);
    }

    public List<UserData> getAllUserData(){
        List<UserPojo> list = service.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            list2.add(convertUserPojoToUserData(p));
        }
        return list2;
    }
}
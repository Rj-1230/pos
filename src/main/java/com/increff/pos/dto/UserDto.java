package com.increff.pos.dto;

import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.NullCheckHelper.checkNullable;
import static com.increff.pos.helper.UserDtoHelper.*;
@Service
public class UserDto {
    @Autowired
    UserService userService;

    public void addUser(UserForm f) throws ApiException
    {
        checkNullable(f);
        normalize(f);
        UserPojo p = convertUserFormToUserPojo(f);
        userService.add(p);
    }

    public void deleteUser(Integer id)
    {
        userService.delete(id);
    }

    public UserData getUser(int id) throws ApiException {
        UserPojo p = userService.getCheck(id);
        return convertUserPojoToUserData(p);
    }

    public void updateUser(@PathVariable int id, @RequestBody UserForm f) throws ApiException {
        checkNullable(f);
        normalize(f);
        UserPojo p = convertUserFormToUserPojo(f);
        userService.update(id,p);
    }

    public List<UserData> getAllUsers(){
        return getAllUserData(userService.getAll());
    }
}
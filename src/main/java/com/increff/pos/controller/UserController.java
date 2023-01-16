package com.increff.pos.controller;

import com.increff.pos.dto.UserDto;
import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
public class UserController {

    @Autowired
    private UserDto userDto;

    @ApiOperation(value="Adding a product")
    @RequestMapping(path="/api/user", method = RequestMethod.POST)
    public void add(@RequestBody UserForm f)throws ApiException {
        userDto.addUser(f);
    }

    @ApiOperation(value="Deleting a product")
    @RequestMapping(path="/api/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        userDto.deleteUser(id);
    }

    @ApiOperation(value="Getting details of a product from id")
    @RequestMapping(path="/api/user", method = RequestMethod.GET)
    public List<UserData> get() throws ApiException {
        return userDto.getAllUserData();
        //before returning , we need to convert our ProductPojo type data into ProductData format
    }
}

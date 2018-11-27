package com.mao.security.web.controller;

import com.mao.security.web.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/info/{id:\\d+}")
    public User getInfo (@PathVariable String id){
        User user =new User();
        user.setId(id);
        user.setName("tom");
        user.setUsername("tom");
        return user;
    }

}

package com.journal.Journal.controller;
import com.journal.Journal.entity.User;
import com.journal.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //UserDetails
    @GetMapping("/profile")
    public Object getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUserName(authentication.getName());
    }

}
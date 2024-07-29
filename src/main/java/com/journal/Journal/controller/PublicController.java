package com.journal.Journal.controller;

import com.journal.Journal.entity.User;
import com.journal.Journal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    //Health check
    @GetMapping("/health")
    public String healthCheck(){
        return "OK";
    }

    //Create User
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<>("User"+user.getId()+"added.", HttpStatus.CREATED);
    }

}

package com.journal.Journal.controller;

import com.journal.Journal.entity.User;
import com.journal.Journal.repository.UserRepository;
import com.journal.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<>("User"+user.getId()+"added.",HttpStatus.CREATED);
    }
}
package com.journal.Journal.controller;
import com.journal.Journal.entity.User;
import com.journal.Journal.service.UserService;
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
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //Create User
    @PostMapping("create-user")
    public ResponseEntity<?> addUser(@RequestBody User user){
       if(userService.findByUserName(user.getUserName()) == null){
           userService.saveUser(user);
           return new ResponseEntity<>("User with ID "+user.getId()+" has successfully created.", HttpStatus.CREATED);
       }
        return new ResponseEntity<>("User name already exist",HttpStatus.BAD_REQUEST);
    }

}

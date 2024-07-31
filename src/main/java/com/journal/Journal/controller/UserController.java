package com.journal.Journal.controller;
import com.journal.Journal.entity.User;
import com.journal.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    //UserDetails
    @GetMapping
    public ResponseEntity<User> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userService.findByUserName(authentication.getName()),HttpStatus.FOUND);
    }
    //Update User
    @PutMapping
    public ResponseEntity<String> updateUserData(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user1 = userService.findByUserName(authentication.getName());
        user1.setUserName(user.getUserName());
        user1.setPassword(user.getPassword());
        userService.saveUser(user1);
        return new ResponseEntity<>("User information updated successfully.", HttpStatus.OK);
    }
    //Delete User
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userService.deleteByUserName(authentication.getName())){
            return new ResponseEntity<>("User Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);
    }

}
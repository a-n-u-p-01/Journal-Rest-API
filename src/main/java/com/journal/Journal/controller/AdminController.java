package com.journal.Journal.controller;

import com.journal.Journal.dto.UserDTO;
import com.journal.Journal.entity.User;
import com.journal.Journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    //Get All User
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    //Delete User
    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){
       
        // Attempt to delete the user
        if (userService.deleteByUserName(userName)) {
            return new ResponseEntity<>("User Deleted", HttpStatus.OK);
        }
        
        // Check the reason for failure
        User user = userService.findByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>("Cannot delete ADMIN user", HttpStatus.FORBIDDEN);
    }

}

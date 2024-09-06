package com.journal.Journal.controller;

import com.journal.Journal.dto.UserDTO;
import com.journal.Journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        userService.deleteByUserName(userName);
        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }
    
    //Assign Admin Role
    @PostMapping("/assign-admin")
    public ResponseEntity<?> transferAdminRole(@RequestBody Map<String, Object> requestBody) {
    	Object newAdminUserNameObj = requestBody.get("newAdminUserName");
    	
    	// Check if the value is a String
        if (!(newAdminUserNameObj instanceof String)) {
            return new ResponseEntity<>("Invalid newAdminUserName", HttpStatus.BAD_REQUEST);
        }
    	
        String newAdminUserName = (String) newAdminUserNameObj; 
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	if(userService.assignAdminRole(authentication.getName(), newAdminUserName)) {
            return new ResponseEntity<>("Admin role assigned successfully", HttpStatus.OK);
    	}
    	return new ResponseEntity<>("The user " + newAdminUserName + " is already an admin.", HttpStatus.CONFLICT);
    }

}

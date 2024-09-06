package com.journal.Journal.service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.journal.Journal.dto.UserDTO;
import com.journal.Journal.entity.User;
import com.journal.Journal.exception.RoleTransferRequiredException;
import com.journal.Journal.exception.UserNotFoundException;
import com.journal.Journal.repository.JournalEntryRepository;
import com.journal.Journal.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //save user
    public void saveUser(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
   //save admin
    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }


    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(),user.getUserName()))
                .collect(Collectors.toList());
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }
    
    public void deleteByUserName(String userName) {
    	User userToDelete = userRepository.findByUserName(userName);
    	
    	if(userToDelete == null) {
    		throw new UserNotFoundException("User with username " + userName + " not found.");
    	}
    	
        boolean hasAdminRole = userToDelete.getRoles().stream().anyMatch(role-> role.equals("ADMIN"));           
        
        // Require role transfer
        if (hasAdminRole && countAdmins() <= 1) {
            throw new RoleTransferRequiredException("Cannot delete the last admin. Please transfer the admin role before proceeding.");
        }
        
        userToDelete.getJournalEntries().forEach(journalEntry -> journalEntryRepository.deleteById(journalEntry.getId()));
    	userRepository.delete(userToDelete);
    }
    
    public boolean assignAdminRole(String currUserName, String newAdminUserName) { 	
    	User newAdmin = userRepository.findByUserName(newAdminUserName);
    	
    	if(newAdmin == null) {
    		throw new UserNotFoundException("User with username " + newAdminUserName + " not found.");
    	} 
    	
    	if(currUserName.equals(newAdminUserName) ||  newAdmin.getRoles().contains("ADMIN")) {
    		return false;
    	}
    	  	
    	newAdmin.getRoles().add("ADMIN");
    	userRepository.save(newAdmin);  
    	return true;
    }
    
    public long countAdmins() {
    	return userRepository.countByRoles("ADMIN");
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
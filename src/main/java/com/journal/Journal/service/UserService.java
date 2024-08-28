package com.journal.Journal.service;
import com.journal.Journal.dto.UserDTO;
import com.journal.Journal.entity.User;
import com.journal.Journal.repository.JournalEntryRepository;
import com.journal.Journal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public boolean deleteByUserName(String userName) {
        boolean hasAdminRole = userRepository.findByUserName(userName).getRoles().stream().anyMatch(role-> role.equals("ADMIN"));
        if(!hasAdminRole){
            userRepository.findByUserName(userName).getJournalEntries().forEach(journalEntry -> journalEntryRepository.deleteById(journalEntry.getId()));
            userRepository.deleteByUserName(userName);
            return true;
        }
        return false;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
package com.journal.Journal.controller;

import com.journal.Journal.entity.JournalEntry;
import com.journal.Journal.entity.User;
import com.journal.Journal.service.JournalEntryService;
import com.journal.Journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    //Get All Journal of user
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournals(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<JournalEntry> journalEntries = userService.findByUserName(authentication.getName()).getJournalEntries();
        return new ResponseEntity<>(journalEntries,HttpStatus.FOUND);
    }
    //Get Journal by id
    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    //Create Journal
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        try {
            journalEntryService.saveEntry(myEntry,user);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //Delete Journal
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(journalEntryService.deleteById(id,authentication.getName())){
            return new ResponseEntity<>("Journal Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Journal Deleted", HttpStatus.BAD_REQUEST);
    }
    //Update Journal
    @PutMapping("{id}")
    public ResponseEntity<?> updateJournal(@RequestBody JournalEntry journal,@PathVariable ObjectId id){
        if(journalEntryService.updateEntry(journal,id)){
            return new ResponseEntity<>("Journal Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>("Journal Updated",HttpStatus.OK);
    }



}
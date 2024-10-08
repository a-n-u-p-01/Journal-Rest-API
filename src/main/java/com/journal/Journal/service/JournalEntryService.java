package com.journal.Journal.service;

import com.journal.Journal.entity.JournalEntry;
import com.journal.Journal.entity.User;
import com.journal.Journal.repository.JournalEntryRepository;
import com.journal.Journal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    //Save journal of user
    public void saveEntry(JournalEntry journalEntry, User user) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }

    //Update journal
    public boolean updateEntry(JournalEntry journalEntry, ObjectId id) {
        if(journalEntryRepository.findById(id).isPresent()){
            journalEntry.setId(id);
            journalEntry.setDate(journalEntryRepository.findById(id).get().getDate());
            journalEntryRepository.save(journalEntry);
            return true;
        }
        return false;
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userRepository.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userRepository.save(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Error ",e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }

}
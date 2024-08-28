package com.journal.Journal.controller;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @Autowired
    private MongoDatabase mongoDatabase;

    //Check health of the application
    @GetMapping
    public ResponseEntity<String> checkHealth() {
        if (checkMongoDB()) {
            return new ResponseEntity<>("Application is healthy", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Application is not healthy", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    //check the connection between database and application
    private boolean checkMongoDB() {
        try {
            mongoDatabase.runCommand(new org.bson.Document("ping", 1));
            return true;
        } catch (MongoClientException e) {
            return false;
        }
    }
}

package com.journal.Journal.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.journal.Journal.utils.ObjectIdSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}

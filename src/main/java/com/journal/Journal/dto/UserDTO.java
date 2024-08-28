package com.journal.Journal.dto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.journal.Journal.utils.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String userName;
}

package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@Data
public class Users {
    @Id
    private ObjectId user_id;
    private String email;
    private String password;
    private String roles;
}

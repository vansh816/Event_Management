package net.engineeringdigest.journalApp.entity;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document(collection = "Users")
@Data
public class Users {
    @Id
    private ObjectId user_id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private String roles;
}

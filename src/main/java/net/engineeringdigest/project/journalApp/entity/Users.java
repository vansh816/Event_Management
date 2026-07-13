package net.engineeringdigest.project.journalApp.entity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;

@Document(collection = "Users")
public class Users {

    @Id
    private ObjectId user_id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private String roles;

    public @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty String email) {
        this.email = email;
    }

    public ObjectId getUser_id() {
        return user_id;
    }

    public void setUser_id(ObjectId user_id) {
        this.user_id = user_id;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

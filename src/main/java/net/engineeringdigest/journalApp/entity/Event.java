package net.engineeringdigest.journalApp.entity;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Date;
@Document(collection = "Event")
@Data
@NotEmpty
public class Event{//MODEL
    private String event_name;
    @Id
    private ObjectId _id;
    private String organiser_name;
    LocalDate today = LocalDate.now(); //date aur time apne aap set honge
    @Indexed(unique = true) //Email can't be same for two organiser
    @Email
    private String organiseremail;
    private String organiser_phn;
    private String location;
    private Integer price;
    private Integer expected_guests;
    private boolean Decoration;
    private boolean Dj_Music;

  }

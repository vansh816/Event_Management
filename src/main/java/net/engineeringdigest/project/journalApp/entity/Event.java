package net.engineeringdigest.project.journalApp.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Document(collection = "Event")
@Data
@NotEmpty
public class Event{//MODEL

    @Id
    private ObjectId _id;
   // @Schema(description = "name") swagger mei dikhega
    private String event_name;
    private String event_description;
    private String organiser_name;
    private String eventdate;
    @Indexed(unique = true) //Email can't be same for two organiser
    @Email
    private String organiseremail;
    private String organiser_phn;
    private String location;
    private Integer expected_guests;
    public int total_price;
    private boolean vipdecoration;
    private boolean djMusic;
    private boolean security;
    private boolean photography;
}

package net.engineeringdigest.journalApp.entity;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.Date;
@Document(collection = "Event")
@Data
public class Event{//MODEL

    private String event_name;
    @Id
    private ObjectId _id;
    private String event_id;
    private String manager_name;
    private String manager_id;
    private String organiser_name;
    private String organizer_id;
    private String date; //date aur time apne aap set hone chaiye
    @Indexed(unique = true) //Email can't be same for two organiser
    private String organiseremail;
    private String organiser_phn;
    private String location;
    private Integer price;
  }

package net.engineeringdigest.journalApp.Controller;
import net.engineeringdigest.journalApp.Repository.EventRepository;
import net.engineeringdigest.journalApp.entity.Event;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.EventService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.util.Instantiator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SocketHandler;

@RestController
@RequestMapping()
public class EventController {
    private Integer available_events=1000;
    private int count=0;

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/admin/get")//Admin ke liye
    public ResponseEntity<?> getallevents(){
        List<Event> all=eventService.getAll();
        if(!all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{email}")//USER apna event nikal skta h email se
    public ResponseEntity<Event> getOrganiserbyemail(@PathVariable String email){
    Event old=eventRepository.findByOrganiseremail(email).orElse(null);
    if(old!=null){
        System.out.println("Event Found");
        return new ResponseEntity<>(old,HttpStatus.OK);
    }
    else {
        System.out.println("Event Not Found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }

    @GetMapping("/user/id/{myid}")//User also see his/her event with id
    public ResponseEntity<Event> getEventDetailsbyid(@PathVariable ObjectId myid){
        Event old= eventService.findByid(myid).orElse(null);
        if(old!=null){
            System.out.println("Event found");
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        else {
            System.out.println("Event Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
    }

  @PostMapping("/User/book")//User event book krega
    public ResponseEntity<Event> bookevent(@RequestBody Event newevent){
           Event old=eventRepository.findBydate(newevent.getDate()).orElse(null);
           Event old1=eventRepository.findByOrganiseremail(newevent.getOrganiseremail()).orElse(null);
           //double booking check hogyi
      if(available_events<=1000){
               if(old!=null && old1!=null){
               System.out.println("Event already Exist");
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
               }
               else{
              available_events=available_events-1;
              count++;
               return new ResponseEntity<>(HttpStatus.OK);
               }}
      else{ System.out.println("Events are full");
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
    }
    @PutMapping("/User/id/{myid}")//User id aur email dono se change kr skta h
    public ResponseEntity<Event> update(@RequestBody Event newevent,@PathVariable ObjectId myid){
        Event old=eventService.findByid(myid).orElse(null);
        if(old!=null){
            old.setEvent_id(newevent.getEvent_id()!=null && !newevent.getEvent_id().equals("")?newevent.getEvent_id(): old.getEvent_id());
            old.setEvent_name(newevent.getEvent_name()!=null && !newevent.getEvent_name().isEmpty()?newevent.getEvent_name(): old.getEvent_name());
            old.setLocation(newevent.getLocation()!=null && !newevent.getLocation().isEmpty()?newevent.getLocation(): old.getLocation());
            old.setPrice(newevent.getPrice()!=null && !newevent.getPrice().equals("")?newevent.getPrice(): old.getPrice());
            old.setManager_name(newevent.getManager_name()!=null && !newevent.getManager_name().isEmpty()?newevent.getManager_name(): old.getManager_name());
            old.setManager_id(newevent.getManager_id()!=null && !newevent.getManager_id().equals("")?newevent.getManager_id(): old.getManager_id());
            old.setOrganiser_name(newevent.getOrganiser_name()!=null && !newevent.getOrganiser_name().isEmpty()?newevent.getOrganiser_name(): old.getOrganiser_name());
            old.setOrganiser_phn(newevent.getOrganiser_phn()!=null && !newevent.getOrganiser_phn().isEmpty()?newevent.getOrganiser_phn(): old.getOrganiser_phn());
            old.setOrganiseremail(newevent.getOrganiseremail()!=null && !newevent.getOrganiseremail().isEmpty()?newevent.getOrganiseremail(): old.getOrganiseremail());
           // old.setTotal_seats(newevent.getTotal_seats()!=null && !newevent.getTotal_seats().equals("")?newevent.getTotal_seats(): old.getTotal_seats());
            old.setOrganizer_id(newevent.getOrganizer_id()!=null && !newevent.getOrganizer_id().equals("")?newevent.getOrganizer_id(): old.getOrganizer_id());
            eventService.createEvent(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            System.out.println("Event Not Found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/id/{myid}")//User aur admin dono ke liye
    public ResponseEntity<?> delete(@PathVariable ObjectId myid){
        Event old=eventService.findByid(myid).orElse(null);
        if(old!=null){
            System.out.println("Event is Found");
            eventService.deleteEvent(myid);
            available_events=available_events+1;
            count--;
            System.out.println("Event is deleted");
            return new ResponseEntity<>(old,HttpStatus.OK);}
        else {System.out.println("Event Not Found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
    }
    @GetMapping("/admin/total_events")
    public int events(){
        return count;
    }
//    @PostMapping("/admin/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody Users user) {
//        String token = userService.verify(user);
//
//        if (!"fail".equals(token)) {
//            Map<String, String> body = new HashMap<>();
//            body.put("token", token);  // JSON object with token
//            return new ResponseEntity<>(body, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }
}

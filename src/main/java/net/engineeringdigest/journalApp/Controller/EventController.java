package net.engineeringdigest.journalApp.Controller;
import net.engineeringdigest.journalApp.EventPayment.EventPayment;
import net.engineeringdigest.journalApp.Repository.EventRepository;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.entity.Event;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.EventService;
import net.engineeringdigest.journalApp.service.UserService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.util.Instantiator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserRepo userrepo;
    @Autowired
    private EventPayment eventPayment;

    @GetMapping("/admin/get")//Admin ke liye
    public ResponseEntity<?> getallevents(){
        List<Event> all=eventService.getAll();
        if(!all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user")//USER apna event nikal skta h email se
    public ResponseEntity<?> getOrganiserbyemail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //  Logged in user fetch
        String email = auth.getName();
     Event old=eventRepository.findByOrganiseremail(email).orElse(null);
    if(old==null){
        return new ResponseEntity<>("event not found",HttpStatus.OK);}
        return new ResponseEntity<>(old, HttpStatus.OK);}

    @PostMapping("/User/book")
    public ResponseEntity<?> bookevent(@RequestBody Event newevent){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//logged-in user ka email
        String email = authentication.getName();
        Users user = userrepo.findByEmail(email).orElse(null);
        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Event oldByDate = eventRepository.findByToday(newevent.getToday()).orElse(null);
        Event oldByEmail = eventRepository.findByOrganiseremail(newevent.getOrganiseremail()).orElse(null);
        if(available_events <= 1000){
            if(oldByDate!=null&& oldByEmail != null){
                return new ResponseEntity<>("event already exist", HttpStatus.CONFLICT);
            }
            if(newevent.getOrganiseremail().equals(email)){  // ownership check
                System.out.println("event found");
                eventRepository.save(newevent);
                available_events--;
                count++;
                return new ResponseEntity<>(newevent, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("unauthorised", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Events are full", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/User/update/{myid}") // User id aur email dono se change kr skta h
    public ResponseEntity<?> update(@RequestBody Event newevent, @PathVariable ObjectId myid){
        Event old=eventService.findByid(myid).orElse(null);//Event fetch
        if(old == null){
            return new ResponseEntity<>("Event Not Found", HttpStatus.NOT_FOUND);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //  Logged in user fetch
        String email = auth.getName();
        Users user = userrepo.findByEmail(newevent.getOrganiseremail()).orElse(null);
        if(!old.getOrganiseremail().equals(email)){// Ownership check
            return new ResponseEntity<>("Unauthorized: You are not the owner", HttpStatus.UNAUTHORIZED);
        }
        else{
        old.setEvent_name(newevent.getEvent_name() != null && !newevent.getEvent_name().isEmpty() ? newevent.getEvent_name() : old.getEvent_name());
        old.setLocation(newevent.getLocation() != null && !newevent.getLocation().isEmpty() ? newevent.getLocation() : old.getLocation());
        old.setPrice(newevent.getPrice() != null ? newevent.getPrice() : old.getPrice());
        old.setOrganiser_name(newevent.getOrganiser_name() != null && !newevent.getOrganiser_name().isEmpty() ? newevent.getOrganiser_name() : old.getOrganiser_name());
        old.setOrganiser_phn(newevent.getOrganiser_phn() != null && !newevent.getOrganiser_phn().isEmpty() ? newevent.getOrganiser_phn() : old.getOrganiser_phn());
        old.setOrganiseremail(newevent.getOrganiseremail() != null && !newevent.getOrganiseremail().isEmpty() ? newevent.getOrganiseremail() : old.getOrganiseremail());
        old.setExpected_guests(newevent.getExpected_guests() != null ? newevent.getExpected_guests() : old.getExpected_guests());
        old.setDecoration(newevent.isDecoration());
        old.setDj_Music(newevent.isDj_Music());
        eventService.createEvent(old);
        return new ResponseEntity<>(old, HttpStatus.OK);}
    }
    @DeleteMapping("/User/id/{myid}") // User aur admin dono ke liye
    public ResponseEntity<?> delete(@PathVariable ObjectId myid){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //  Logged in user fetch
        String email = auth.getName();
        Event old = eventService.findByid(myid).orElse(null);
        if(old == null) {
            return new ResponseEntity<>("event not found",HttpStatus.NO_CONTENT);
        }
        if(!old.getOrganiseremail().equals(email)){// Ownership check
            return new ResponseEntity<>("Unauthorized: You are not the owner", HttpStatus.UNAUTHORIZED);
        }
        eventService.deleteEvent(myid);
        available_events++;
        count--;
        System.out.println("Event is deleted");
        return new ResponseEntity<>(old, HttpStatus.OK);
    }

    @GetMapping("/admin/total_events")
    public int used_events(){
        return count;
    }
    @GetMapping("/admin/rem_events")
    public int rem_events(){
        return available_events;
    }
    @PostMapping("/User/pay/{_id}")
    public ResponseEntity<?> pay(@PathVariable ObjectId _id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//logged-in user ka email
        String email = authentication.getName();//parvesh
       Event event = eventRepository.findById(_id).orElse(null);
        Users user = userrepo.findByEmail(email).orElse(null);//nhi h parvesh
        if(event == null){
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
        if(!event.getOrganiseremail().equals(email)){// Ownership check
            return new ResponseEntity<>("Unauthorized: You are not the owner", HttpStatus.UNAUTHORIZED);
        }
        eventPayment.setPayment_status(true);//Payment success maan le
        eventPayment.setBooking_status(true);
        return new ResponseEntity<>("Payment Done & Booking Confirmed", HttpStatus.OK);
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

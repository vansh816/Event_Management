package net.engineeringdigest.project.journalApp.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.project.journalApp.EventPayment.EventPayment;
import net.engineeringdigest.project.journalApp.Repository.EventRepository;
import net.engineeringdigest.project.journalApp.Repository.UserRepo;
import net.engineeringdigest.project.journalApp.entity.Event;
import net.engineeringdigest.project.journalApp.entity.Users;
import net.engineeringdigest.project.journalApp.service.EventService;
import net.engineeringdigest.project.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@Tag(name = "User's Event-related APIs")
public class EventController {
    public  Integer available_events=1000;
    public int count=0;

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


    @GetMapping("/user/email")//USER apna event nikal skta h email se
    @Operation(summary = "User can Get event by Email")
    public ResponseEntity<?> getOrganiserbyemail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //  Logged in user fetch
        String email = auth.getName();
        System.out.println("Email from token: " + email);
        Event old=eventRepository.findByOrganiseremail(email).orElse(null);
        if(old==null){
            return new ResponseEntity<>("event not found",HttpStatus.OK);}
        return new ResponseEntity<>(old, HttpStatus.OK);}

    @PostMapping("/User/book")// User book skta h
    @Operation(summary = " user can Book Event")
    public ResponseEntity<?> bookevent(@RequestBody Event newevent){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//logged-in user ka email
        String email = authentication.getName();
        Users user = userrepo.findByEmail(email).orElse(null);
        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Event oldByDate = eventRepository.findByEventdate(newevent.getEventdate()).orElse(null);
        Event oldByEmail = eventRepository.findByOrganiseremail(newevent.getOrganiseremail()).orElse(null);
        if(available_events <= 1000){
            if(oldByDate!=null&& oldByEmail!=null){
                return new ResponseEntity<>("Event already exist.Please try another email and date", HttpStatus.CONFLICT);
            }
            else{
            if(newevent.getOrganiseremail().equals(email)){  // ownership check
                 int price=10000;
                if(newevent.isPhotography()) price+=5000;
                if(newevent.isDjMusic()) price+=5000;
                if(newevent.isSecurity()) price+=5000;
                if(newevent.isVipdecoration()) price+=5000;
                if(newevent.getExpected_guests()>400) price+=5000;
                newevent.setTotal_price(price);
                System.out.println("total price is "+newevent.total_price);
                eventRepository.save(newevent);
                available_events--;
                count++;
                return new ResponseEntity<>(newevent, HttpStatus.OK);}

            else{
                return new ResponseEntity<>("unauthorised", HttpStatus.UNAUTHORIZED);}
        }
        }
        else {
            return new ResponseEntity<>("Events are full", HttpStatus.BAD_REQUEST);}
    }
    @PutMapping("/User/update/{myid}") // User id se change kr skta h
    @Operation(summary = "User can Update event by id")
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
            old.setOrganiser_name(newevent.getOrganiser_name() != null && !newevent.getOrganiser_name().isEmpty() ? newevent.getOrganiser_name() : old.getOrganiser_name());
            old.setOrganiser_phn(newevent.getOrganiser_phn() != null && !newevent.getOrganiser_phn().isEmpty() ? newevent.getOrganiser_phn() : old.getOrganiser_phn());
            old.setOrganiseremail(newevent.getOrganiseremail() != null && !newevent.getOrganiseremail().isEmpty() ? newevent.getOrganiseremail() : old.getOrganiseremail());
            old.setExpected_guests(newevent.getExpected_guests() != null ? newevent.getExpected_guests() : old.getExpected_guests());
            old.setVipdecoration(newevent.isVipdecoration());
            old.setDjMusic(newevent.isDjMusic());
            old.setSecurity(newevent.isSecurity());
            old.setPhotography(newevent.isPhotography());

            eventService.createEvent(old);
            return new ResponseEntity<>(old, HttpStatus.OK);}
    }
    @DeleteMapping("/user/id/{myid}") // User aur admin dono ke liye
    @Operation(summary = " Both User and Admin can Delete event by id")
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

    @PostMapping("/user/pay/{id}")
    @Operation(summary = " Both User and Admin can Check Payment Status")
    public ResponseEntity<?> pay(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//logged-in user ka email
        String email = authentication.getName();//parvesh
        Event event = eventRepository.findById(id).orElse(null);
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
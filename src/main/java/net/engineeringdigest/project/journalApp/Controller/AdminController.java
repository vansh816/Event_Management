package net.engineeringdigest.journalApp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Repository.EventRepository;
import net.engineeringdigest.journalApp.entity.Event;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.EventService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "Admin's APIs")
public class AdminController {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventController eventController;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/all_events")//Admin ke liye
    @Operation(summary = "Get all the Events in the System ")
    public ResponseEntity<?> getallevents(){
        List<Event> all=eventService.getAll();
        if(!all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/admin/total_events")
    @Operation(summary = "Get Total no. of Events in the System")
    public int used_events(){
        return eventController.count;
    }
    @GetMapping("/admin/rem_events")
    @Operation(summary = "Get Remaining no. of Events in the System")
    public int rem_events(){
        return eventController.available_events;
    }

@GetMapping("admin/all-user")//Admin dekh skta h users ko
@Operation(summary = "Get All Users in the System")
public List<Users> all(){
    return userService.findall();
}
}

package net.engineeringdigest.journalApp.Controller;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class UserController {
    public BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(8);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userrepo;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
       return  userService.create(user);
    }
    @PostMapping("/registerA")
    public Users registerA(@RequestBody Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("ADMIN");
        return  userService.create(user);
    }
//    @PostMapping("/login")/////// mera h
//    public String login(@RequestBody Users user){
//        return userService.verify(user);
//    }
@PostMapping("/login") /// ///mera nhi h
public ResponseEntity<Map<String, String>> login(@RequestBody Users user) {
    String token = userService.verify(user);

    if (!"fail".equals(token)) {
        Map<String, String> body = new HashMap<>();
        body.put("token", token);  // JSON object with token
        return new ResponseEntity<>(body, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
    @GetMapping("/all-user")
    public List<Users> all(){
        return userService.findall();
    }
    @DeleteMapping("/{email}")
    public void deleteuser(@PathVariable String email){
        Users user=userrepo.findByEmail(email).orElse(null);
         userService.delete(user);
    }
}

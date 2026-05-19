package net.engineeringdigest.project.journalApp.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.project.journalApp.Repository.UserRepo;
import net.engineeringdigest.project.journalApp.entity.Users;
import net.engineeringdigest.project.journalApp.service.UserService;
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
@Tag(name = "Authentication & Authorization")
public class UserController {
    public BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(8);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userrepo;

    @PostMapping("/register")
    @Operation(summary = "Only User can register")
    public Users register(@RequestBody Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
       return  userService.create(user);
    }
    @PostMapping("/registerA")
    @Operation(summary = "Only Admin can register")
    public Users registerA(@RequestBody Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("ADMIN");
        return  userService.create(user);
    }
@PostMapping("/login")
@Operation(summary = "Both User and Admin can Login")
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
    @DeleteMapping("/{email}")
    @Operation(summary = "Delete User and Admin by Email")
    public void deleteuser(@PathVariable String email){
        Users user=userrepo.findByEmail(email).orElse(null);
         userService.delete(user);
    }}

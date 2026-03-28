package net.engineeringdigest.journalApp.service;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.entity.Users;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authmanager;
    @Autowired
    private JWTService jwtService;

    public  String verify(Users user) {
        Authentication authentication=
                authmanager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
     if(authentication.isAuthenticated()){
         return jwtService.generateToken(user.getUsername());
     }
     return "fail";
    }

    public Users create(Users user){
        return userRepo.save(user);}

    public List<Users> findall() {
        return userRepo.findAll();
    }
    public void delete(Users user){
         userRepo.delete(user);
    }
//    public Optional<Users> find(String username){
//        return userRepo.findBy(username);
//    }
}

package net.engineeringdigest.project.journalApp.service;
import net.engineeringdigest.project.journalApp.Repository.UserRepo;
import net.engineeringdigest.project.journalApp.entity.Users;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.List;

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
                authmanager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
     if(authentication.isAuthenticated()){
         return jwtService.generateToken(user.getEmail());
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

}

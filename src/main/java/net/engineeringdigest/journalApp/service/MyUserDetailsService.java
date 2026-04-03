package net.engineeringdigest.journalApp.service;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.entity.UserPrincipal;
import net.engineeringdigest.journalApp.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo Repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = Repo.findByEmail( email).orElse(null);
        if (user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}

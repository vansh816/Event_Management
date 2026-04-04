package net.engineeringdigest.project.journalApp.Repository;

import net.engineeringdigest.project.journalApp.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {

     Optional<Users> findByEmail(String email);

}
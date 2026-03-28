package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepo extends MongoRepository<Users, String> {
    Users findByUsername(String username);
}
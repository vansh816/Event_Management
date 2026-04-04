package net.engineeringdigest.project.journalApp.Repository;

import net.engineeringdigest.project.journalApp.entity.Event;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EventRepository extends MongoRepository<Event, ObjectId> {
    Optional<Event> findByOrganiseremail(String organiseremail);
    Optional<Event> findByEventdate(String eventdate);

}
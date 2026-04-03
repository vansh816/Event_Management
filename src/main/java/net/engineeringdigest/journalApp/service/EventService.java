package net.engineeringdigest.journalApp.service;


//import net.engineeringdigest.journalApp.EventPayment.EventPayment;
import net.engineeringdigest.journalApp.Repository.EventRepository;
import net.engineeringdigest.journalApp.entity.Event;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAll(){
        return eventRepository.findAll();
    }
    public Event createEvent(Event event){
        return eventRepository.save(event);
    }
    public Event updateEvent(Event event){
      return eventRepository.save(event);

    }
    public boolean deleteEvent(ObjectId myid){
        eventRepository.deleteById(myid);
        return true;
    }
    public Optional<Event> findByid(ObjectId myid) {
        return eventRepository.findById(myid);
    }
}

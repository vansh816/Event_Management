package net.engineeringdigest.journalApp.EventPayment;
import lombok.Data;
import org.springframework.stereotype.Component;
@Component
@Data
public class EventPayment {

    private boolean payment=false;
    String Payment_Status="not done";
    String Booking_Status="not done";



}

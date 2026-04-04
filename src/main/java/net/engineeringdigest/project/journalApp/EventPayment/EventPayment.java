package net.engineeringdigest.project.journalApp.EventPayment;
import lombok.Data;
import org.springframework.stereotype.Component;
@Component
@Data
public class EventPayment {

   private boolean payment=false;
   private boolean payment_status=false;
   private boolean booking_status=false;

}

package net.engineeringdigest.project.journalApp.EventPayment;
import lombok.Data;
import org.springframework.stereotype.Component;
@Component
public class EventPayment {

   private boolean payment=false;
   private boolean payment_status=false;
   private boolean booking_status=false;

   public boolean isPayment() {
      return payment;
   }

   public void setPayment(boolean payment) {
      this.payment = payment;
   }

   public boolean isPayment_status() {
      return payment_status;
   }

   public void setPayment_status(boolean payment_status) {
      this.payment_status = payment_status;
   }

   public boolean isBooking_status() {
      return booking_status;
   }

   public void setBooking_status(boolean booking_status) {
      this.booking_status = booking_status;
   }
}

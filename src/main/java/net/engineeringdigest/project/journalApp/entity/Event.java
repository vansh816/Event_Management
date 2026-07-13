package net.engineeringdigest.project.journalApp.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Document(collection = "Event")
@NotEmpty
public class Event{//MODEL

    @Id
    private ObjectId _id;
   // @Schema(description = "name") swagger mei dikhega
    private String eventname;
    private String event_description;
    private String organiser_name;
    private String eventdate;
    @Indexed(unique = true) //Email can't be same for two organiser
    @Email
    private String organiseremail;
    private String organiser_phn;
    private String location;
    private Integer expected_guests;
    public int total_price;
    private boolean vipdecoration;
    private boolean djMusic;
    private boolean security;
    private boolean photography;

 public ObjectId get_id() {
  return _id;
 }

 public void set_id(ObjectId _id) {
  this._id = _id;
 }

 public String getEventname() {
  return eventname;
 }

 public void setEventname(String eventname) {
  this.eventname = eventname;
 }

 public String getEvent_description() {
  return event_description;
 }

 public void setEvent_description(String event_description) {
  this.event_description = event_description;
 }

 public String getOrganiser_name() {
  return organiser_name;
 }

 public void setOrganiser_name(String organiser_name) {
  this.organiser_name = organiser_name;
 }

 public String getEventdate() {
  return eventdate;
 }

 public void setEventdate(String eventdate) {
  this.eventdate = eventdate;
 }

 public @Email String getOrganiseremail() {
  return organiseremail;
 }

 public void setOrganiseremail(@Email String organiseremail) {
  this.organiseremail = organiseremail;
 }

 public String getOrganiser_phn() {
  return organiser_phn;
 }

 public void setOrganiser_phn(String organiser_phn) {
  this.organiser_phn = organiser_phn;
 }

 public String getLocation() {
  return location;
 }

 public void setLocation(String location) {
  this.location = location;
 }

 public Integer getExpected_guests() {
  return expected_guests;
 }

 public void setExpected_guests(Integer expected_guests) {
  this.expected_guests = expected_guests;
 }

 public int getTotal_price() {
  return total_price;
 }

 public void setTotal_price(int total_price) {
  this.total_price = total_price;
 }

 public boolean isVipdecoration() {
  return vipdecoration;
 }

 public void setVipdecoration(boolean vipdecoration) {
  this.vipdecoration = vipdecoration;
 }

 public boolean isDjMusic() {
  return djMusic;
 }

 public void setDjMusic(boolean djMusic) {
  this.djMusic = djMusic;
 }

 public boolean isSecurity() {
  return security;
 }

 public void setSecurity(boolean security) {
  this.security = security;
 }

 public boolean isPhotography() {
  return photography;
 }

 public void setPhotography(boolean photography) {
  this.photography = photography;
 }
}

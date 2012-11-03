package com.canchita.models;

import java.io.Serializable;
import java.math.*;
import java.text.*;
import java.util.*;
import java.sql.Time;

import javax.persistence.*;

@Entity(name = "SCHEDULE_ITEM")
@Table(name = "schedule_items")
public class ScheduleItem implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public static final String DAYS[] = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name="schedule_id")
  private Schedule schedule;
  private int day;
  private Time start_at;
  private int duration;
  private float price;
  
  @Override
  public boolean equals(Object obj) {
    System.out.println("equals");
    if (obj instanceof ScheduleItem) {
      return ((ScheduleItem)obj).getId() == this.getId();
    }
    else {
      return false;
    }
  }
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Schedule getSchedule() {
    return schedule;
  }
  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }
  public int getDay() {
    return day;
  }
  public void setDay(int day) {
    this.day = day;
  }
  public Time getStart_at() {
    return start_at;
  }
  public void setStart_at(Time start_at) {
    this.start_at = start_at;
  }
  public int getDuration() {
    return duration;
  }
  public void setDuration(int duration) {
    this.duration = duration;
  }
  public float getPrice() {
    return price;
  }
  public void setPrice(float price) {
    this.price = price;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
  
  public String getLabel() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    
    SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
    
    float price = this.price;
    
    BigDecimal formattedPrice = new BigDecimal(price);
    formattedPrice = formattedPrice.setScale(2);
    
    return ScheduleItem.DAYS[this.day - 1] + " - " + sdf_time.format(this.start_at) + " : S/." + formattedPrice;
  }
}

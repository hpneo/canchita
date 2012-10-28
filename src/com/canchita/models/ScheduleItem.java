package com.canchita.models;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.*;

@Entity(name = "SCHEDULE_ITEM")
@Table(name = "schedule_items")
public class ScheduleItem implements Serializable {
  private static final long serialVersionUID = 1L;

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
}

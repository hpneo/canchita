package com.canchita.models;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity(name = "SCHEDULE")
@Table(name = "schedules")
public class Schedule implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private Movie movie;
  @OneToMany(mappedBy="schedule")
  private List<ScheduleItem> schedule_items;
  
  public Schedule() {
//    GregorianCalendar calendar = new GregorianCalendar();
//    calendar.set(Calendar.HOUR, 4);
//    calendar.set(Calendar.AM_PM, Calendar.PM);
//    calendar.set(Calendar.MINUTE, 30);
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Movie getMovie() {
    return movie;
  }
  public void setMovie(Movie movie) {
    this.movie = movie;
  }
  public List<ScheduleItem> getSchedule_items() {
    return schedule_items;
  }
  public void setSchedule_items(List<ScheduleItem> schedule_items) {
    this.schedule_items = schedule_items;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
}

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
  @JoinColumn(name="schedule_items")
  private List<ScheduleItem> scheduleItems;

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
  public List<ScheduleItem> getScheduleItems() {
    return scheduleItems;
  }
  public void setSchedule_items(List<ScheduleItem> scheduleItems) {
    this.scheduleItems = scheduleItems;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
}

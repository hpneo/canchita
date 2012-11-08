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
  @Temporal(value=TemporalType.DATE)
  private Date start_at;
  @Temporal(value=TemporalType.DATE)
  private Date end_at;
  @OneToMany(mappedBy="schedule", fetch=FetchType.LAZY)
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
  public Date getStart_at() {
    return start_at;
  }
  public void setStart_at(Date start_at) {
    this.start_at = start_at;
  }
  public Date getEnd_at() {
    return end_at;
  }
  public void setEnd_at(Date end_at) {
    this.end_at = end_at;
  }
  public List<ScheduleItem> getScheduleItems() {
    return scheduleItems;
  }
  public void setScheduleItems(List<ScheduleItem> scheduleItems) {
    this.scheduleItems = scheduleItems;
  }
}

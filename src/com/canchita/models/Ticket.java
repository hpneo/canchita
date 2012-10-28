package com.canchita.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "TICKET")
@Table(name = "tickets")
public class Ticket implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name="schedule_item_id")
  private ScheduleItem scheduleItem;
  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;
  private int quantity;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public ScheduleItem getScheduleItem() {
    return scheduleItem;
  }
  public void setScheduleItem(ScheduleItem scheduleItem) {
    this.scheduleItem = scheduleItem;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
}

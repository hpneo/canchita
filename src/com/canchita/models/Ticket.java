package com.canchita.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.canchita.dao.UserDAO;

@Entity(name = "TICKET")
@Table(name = "tickets")
@Cacheable(false)
public class Ticket implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name="schedule_item_id")
  private ScheduleItem scheduleItem;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="user_id", referencedColumnName="id")
  private User user;
  private int quantity;
  @Temporal(value=TemporalType.DATE)
  private Date bought_at;
  
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
  
  public Date getBought_at() {
    return bought_at;
  }
  public void setBought_at(Date bought_at) {
    this.bought_at = bought_at;
  }
  
  public String getCode() {
    String code = String.valueOf(this.getId() + "" + this.scheduleItem.getStart_at().getTime());
    return code.substring(0, 9);
  }
  
  public void updateUserPoints() {
    User user = this.getUser();
    user.setPoints(user.getPoints() + 1);
    
    UserDAO userDAO = new UserDAO();
    userDAO.update(user);
  }
}

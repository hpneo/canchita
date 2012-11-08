package com.canchita.models;

import java.util.*;
import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "USER")
@Table(name = "users")
@Cacheable(false)
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private String name;
  private String email;
  private String password;
  private String dni;
  private int points;
  @OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Ticket> tickets;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getDni() {
    return dni;
  }
  public void setDni(String dni) {
    this.dni = dni;
  }
  public int getPoints() {
    return points;
  }
  public void setPoints(int points) {
    this.points = points;
  }
  public List<Ticket> getTickets() {
    return tickets;
  }
  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }
}

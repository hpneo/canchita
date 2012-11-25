package com.canchita.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "ADMIN_USER")
@Table(name = "admin_users")
public class AdminUser implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private String email;
  private String password;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
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
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
}

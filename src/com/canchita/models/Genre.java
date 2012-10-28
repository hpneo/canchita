package com.canchita.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "GENRES")
@Table(name = "genres")
public class Genre implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private String name;
  
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
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
}

package com.canchita.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "GENRE")
@Table(name = "genres")
public class Genre implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private String name;
  
  @Override
  public boolean equals(Object obj) {
    System.out.println("equals");
    if (obj instanceof Genre) {
      return ((Genre)obj).getId() == this.getId();
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
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}

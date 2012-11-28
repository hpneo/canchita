package com.canchita.models;

import java.util.*;
import java.io.Serializable;
import javax.persistence.*;

@Entity(name = "MOVIE")
@Table(name = "movies")
public class Movie implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  @ManyToOne
  @JoinColumn(name="genre_id")
  private Genre genre;
  private float rating;
  private String poster;
  @OneToMany(mappedBy="movie")
  private List<Schedule> schedules;
  @OneToMany(mappedBy="movie")
  private List<Comment> comments;
  
  @Override
  public boolean equals(Object obj) {
    System.out.println("equals");
    if (obj instanceof Movie) {
      return ((Movie)obj).getId() == this.getId();
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
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Genre getGenre() {
    return genre;
  }
  public void setGenre(Genre genre) {
    this.genre = genre;
  }
  public float getRating() {
    return rating;
  }
  public void setRating(float rating) {
    this.rating = rating;
  }
  public String getPoster() {
    return poster;
  }
  public void setPoster(String poster) {
    this.poster = poster;
  }
  public List<Schedule> getSchedules() {
    return schedules;
  }
  public void setSchedules(List<Schedule> schedules) {
    this.schedules = schedules;
  }
  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
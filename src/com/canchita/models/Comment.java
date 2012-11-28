package com.canchita.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "COMMENT")
@Table(name = "comments")
public class Comment implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name="movie_id")
  private Movie movie;
  private String text;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public Movie getMovie() {
    return movie;
  }
  public void setMovie(Movie movie) {
    this.movie = movie;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
}

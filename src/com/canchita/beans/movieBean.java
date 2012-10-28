package com.canchita.beans;

import java.io.Serializable;
import java.util.*;

import com.canchita.dao.*;
import com.canchita.models.*;

import javax.faces.bean.*;

@ManagedBean
@RequestScoped
public class movieBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @ManagedProperty("#{param.id}")
  private int id;
  private Movie movie;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Movie getMovie() {
    MovieDAO movieDAO = new MovieDAO();
    this.movie = movieDAO.find(this.id);
    
    return this.movie;
  }
  public void setMovie(Movie movie) {
    this.movie = movie;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}

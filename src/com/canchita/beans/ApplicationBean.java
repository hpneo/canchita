package com.canchita.beans;

import java.io.Serializable;
import java.util.*;

import com.canchita.dao.*;
import com.canchita.models.*;

import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class ApplicationBean implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<Movie> lastMovies;

  public List<Movie> getLastMovies() {
    MovieDAO movieDAO = new MovieDAO();
    this.lastMovies = movieDAO.list();
    return this.lastMovies;
  }
  public void setLastMovies(List<Movie> lastMovies) {
    this.lastMovies = lastMovies;
  }
}

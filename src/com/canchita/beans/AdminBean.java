package com.canchita.beans;

import java.io.Serializable;
import java.util.*;

import com.canchita.dao.*;
import com.canchita.models.*;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@SessionScoped
@RequestScoped
public class AdminBean implements Serializable {
  private static final long serialVersionUID = 1L;

  @ManagedProperty("#{param.movie_id}")
  private int movieId;
  @ManagedProperty("#{param.schedule_id}")
  private int scheduleId;
  
  private List<Movie> movies;
  private List<Schedule> schedules;
  private List<User> users;

  private Movie movie;
  private Schedule schedule;
  
  private String movieName;
  private String movieDescription;
  private Genre movieGenre;
  
  public String saveMovie() {
    this.movie.setName(this.movieName);
    this.movie.setDescription(this.movieDescription);
    this.movie.setGenre(this.movieGenre);
    
    if (this.movie.getId() == 0) {
      this.createMovie();
    }
    else {
      this.updateMovie();
    }
    
    return "/admin/movies.xhtml?faces-redirect=true";
  }
  
  private void createMovie() {
    System.out.println("createMovie =======================");
    MovieDAO movieDAO = new MovieDAO();
    movieDAO.create(this.movie);
    System.out.println("===================================");
  }
  
  private void updateMovie() {
    System.out.println("updateMovie =======================");
    MovieDAO movieDAO = new MovieDAO();
    movieDAO.update(this.movie);
    System.out.println("===================================");
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public int getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(int scheduleId) {
    this.scheduleId = scheduleId;
  }
  
  public List<Movie> getMovies() {
    MovieDAO movieDAO = new MovieDAO();
    this.movies = movieDAO.list();
    return this.movies;
  }

  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }
  
  public List<Schedule> getSchedules() {
    ScheduleDAO scheduleDAO = new ScheduleDAO();
    this.schedules = scheduleDAO.list();
    return this.schedules;
  }

  public void setSchedules(List<Schedule> schedules) {
    this.schedules = schedules;
  }

  public List<User> getUsers() {
    UserDAO userDAO = new UserDAO();
    this.users = userDAO.list();
    return this.users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public Movie getMovie() {
    MovieDAO movieDAO = new MovieDAO();
    if (this.movieId == 0) {
      this.movie = new Movie();
    }
    else {
      this.movie = movieDAO.find(this.movieId);
    }
    
    return this.movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public String getMovieName() {
    return this.getMovie().getName();
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  public String getMovieDescription() {
    return this.getMovie().getDescription();
  }

  public void setMovieDescription(String movieDescription) {
    this.movieDescription = movieDescription;
  }

  public Genre getMovieGenre() {
    return this.getMovie().getGenre();
  }

  public void setMovieGenre(Genre movieGenre) {
    this.movieGenre = movieGenre;
  }

  private HttpServletRequest getRequest() {
    return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }
  
  private HttpServletResponse getResponse() {
    return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
  }
  
  private Cookie findCookie(String cookieName) {
    Cookie cookie = null;
    Cookie[] cookies = this.getRequest().getCookies();
    if(cookies != null) {
      for(int i = 0; i < cookies.length; i++) {
        if(cookies[i].getName().equals(cookieName)) {
          cookie = cookies[i];
        }
      }
    }
    return cookie;
  }
}

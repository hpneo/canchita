package com.canchita.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;

import com.canchita.dao.*;
import com.canchita.models.*;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
@RequestScoped
public class AdminBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private UploadedFile moviePosterFile;

  @ManagedProperty("#{param.movie_id}")
  private int movieId;
  @ManagedProperty("#{param.schedule_id}")
  private int scheduleId;
  
  private List<Movie> movies;
  private List<Schedule> schedules;
  private List<User> users;
  private List<Ticket> tickets;

  private Movie movie;
  private Schedule schedule;
  
  private String movieName;
  private String movieDescription;
  private Genre movieGenre;
  
  private Movie scheduleMovie;
  private Date scheduleStartAt;
  private Date scheduleEndAt;
  
  private void copyUploadedFile(UploadedFile uploadedFile) {
    try {
      File targetFolder = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/posters"));
      
      File destination = new File(targetFolder, this.parameterizeString(this.movieName) + ".jpg");
      
      OutputStream outputstream = new FileOutputStream(destination);
      
      System.out.println(destination.getAbsolutePath());
      
      int read = 0;
      
      InputStream stream;
      stream = uploadedFile.getInputstream();
      byte[] buffer = new byte[(int) this.getMoviePosterFile().getSize() ];
      
      while ((read = stream.read(buffer)) != -1) {
        outputstream.write(buffer, 0, read);
      }
      
      stream.close();
      outputstream.flush();
      outputstream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private String parameterizeString(String string) {
    String parameterizedString = string.toLowerCase();

    parameterizedString = parameterizedString.replace("(", "");
    parameterizedString = parameterizedString.replace(")", "");
    parameterizedString = parameterizedString.replace(" ", "-");
    
    return parameterizedString;
  }
  
  public String saveMovie() {
    this.movie.setName(this.movieName);
    this.movie.setDescription(this.movieDescription);
    this.movie.setGenre(this.movieGenre);
    
    try {
      
      this.copyUploadedFile(this.getMoviePosterFile());
      this.movie.setPoster(this.parameterizeString(this.movieName) + ".jpg");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    if (this.movie.getId() == 0) {
      this.createMovie();
    }
    else {
      this.updateMovie();
    }
    
    return "/admin/movies.xhtml?faces-redirect=true";
  }
  
  private void createMovie() {
    MovieDAO movieDAO = new MovieDAO();
    movieDAO.create(this.movie);
  }
  
  private void updateMovie() {
    MovieDAO movieDAO = new MovieDAO();
    movieDAO.update(this.movie);
  }
  
  public void uploadMoviePoster() {
    System.out.println(this.moviePosterFile);
  }
  
  public String saveSchedule() {
    this.schedule.setMovie(this.scheduleMovie);
    this.schedule.setStart_at(this.scheduleStartAt);
    this.schedule.setEnd_at(this.scheduleEndAt);
    
    if (this.schedule.getId() == 0) {
      this.createSchedule();
    }
    else {
      this.updateSchedule();
    }
    
    return "/admin/schedules.xhtml?faces-redirect=true";
  }
  
  private void createSchedule() {
    ScheduleDAO scheduleDAO = new ScheduleDAO();
    scheduleDAO.create(this.schedule);
  }
  
  private void updateSchedule() {
    ScheduleDAO scheduleDAO = new ScheduleDAO();
    scheduleDAO.update(this.schedule);
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
  
  public List<Ticket> getTickets() {
    TicketDAO ticketDAO = new TicketDAO();
    this.tickets = ticketDAO.list();
    return this.tickets;
  }
  
  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }
  
  public StatsBean getStatsBean() {
    return new StatsBean(this.getTickets());
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
    ScheduleDAO scheduleDAO = new ScheduleDAO();
    if (this.scheduleId == 0) {
      this.schedule = new Schedule();
    }
    else {
      this.schedule = scheduleDAO.find(this.scheduleId);
    }
    
    return this.schedule;
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

  public UploadedFile getMoviePosterFile() {
    return moviePosterFile;
  }

  public void setMoviePosterFile(UploadedFile moviePosterFile) {
    this.moviePosterFile = moviePosterFile;
  }

  public Movie getScheduleMovie() {
    return this.getSchedule().getMovie();
  }

  public void setScheduleMovie(Movie scheduleMovie) {
    this.scheduleMovie = scheduleMovie;
  }

  public Date getScheduleStartAt() {
    return this.getSchedule().getStart_at();
  }

  public void setScheduleStartAt(Date scheduleStartAt) {
    this.scheduleStartAt = scheduleStartAt;
  }

  public Date getScheduleEndAt() {
    return this.getSchedule().getEnd_at();
  }

  public void setScheduleEndAt(Date scheduleEndAt) {
    this.scheduleEndAt = scheduleEndAt;
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

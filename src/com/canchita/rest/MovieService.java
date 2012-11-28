package com.canchita.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.canchita.models.*;
import com.canchita.dao.*;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MovieService {

  @GET
  public List<Movie> listMovies() {
    MovieDAO movieDAO = new MovieDAO();
    List<Movie> records = movieDAO.list();
    List<Movie> list = new ArrayList<Movie>();
    
    for(int i = 0; i < records.size(); i++) {
      Movie item = new Movie();

      item.setId(records.get(i).getId());
      item.setName(records.get(i).getName());
      item.setDescription(records.get(i).getDescription());
      item.setGenre(records.get(i).getGenre());
      item.setRating(records.get(i).getRating());
      item.setPoster(records.get(i).getPoster());
      
      list.add(item);
    }
    
    return list;
  }
  
  @GET
  @Path("{id}")
  public Movie getMovie(@PathParam("id") String id) {
    MovieDAO movieDAO = new MovieDAO();
    
    int movieId = new Integer(id).intValue();
    
    Movie movie = new Movie();
    Movie record = movieDAO.find(movieId);
    
    movie.setId(record.getId());
    movie.setName(record.getName());
    movie.setDescription(record.getDescription());
    movie.setGenre(record.getGenre());
    movie.setRating(record.getRating());
    movie.setPoster(record.getPoster());
    
    return movie;
  }
  
  @GET
  @Path("{id}/schedule_items")
  public List<ScheduleItem> getScheduleItems(@PathParam("id") String id) {
    MovieDAO movieDAO = new MovieDAO();
    
    int movieId = new Integer(id).intValue();
    
    List<Schedule> schedules = movieDAO.find(movieId).getSchedules();
    
    List<ScheduleItem> records = schedules.get(schedules.size() - 1).getScheduleItems();
    List<ScheduleItem> list = new ArrayList<ScheduleItem>();
    
    for(int i = 0; i < records.size(); i++) {
      ScheduleItem item = new ScheduleItem();

      item.setId(records.get(i).getId());
      item.setDuration(records.get(i).getDuration());
      item.setPrice(records.get(i).getPrice());
      item.setStart_at(records.get(i).getStart_at());
      
      list.add(item);
    }
    
    return list;
  }
  
  @GET
  @Path("{id}/comments")
  public List<Comment> getComments(@PathParam("id") String id) {
    MovieDAO movieDAO = new MovieDAO();
    
    int movieId = new Integer(id).intValue();
    
    List<Comment> records = movieDAO.find(movieId).getComments();
    List<Comment> list = new ArrayList<Comment>();
    
    for(int i = 0; i < records.size(); i++) {
      Comment item = new Comment();

      item.setId(records.get(i).getId());
      item.setText(records.get(i).getText());
      item.setUser(sanitizeUser(records.get(i).getUser()));
      
      list.add(item);
    }
    
    return list;
  }
  
  private User sanitizeUser(User user) {
    user.setTickets(null);
    user.setPassword(null);
    
    return user;
  }

}

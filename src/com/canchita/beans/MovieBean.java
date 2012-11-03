package com.canchita.beans;

import java.io.Serializable;

import com.canchita.dao.*;
import com.canchita.models.*;
import com.canchita.converters.*;

import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.convert.Converter;
import javax.servlet.http.*;

@ManagedBean
@RequestScoped
public class MovieBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  //private static final String CONTEXT_PATH = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
  
  @ManagedProperty("#{param.id}")
  private int id;
  private Movie movie;
  private Schedule currentSchedule;
  private ScheduleItem scheduleItem;
  
  public String buyTicket() {
    Cookie cookie = this.findCookie("current_user");
    
    if(cookie == null) {
      return "auth?faces-redirect=true";
    }
    else {
      TicketDAO ticketDAO = new TicketDAO();
      Ticket ticket = new Ticket();
      ticket.setScheduleItem(this.scheduleItem);
      ticket.setQuantity(1);
      ticket.setUser(new ApplicationBean().getCurrentUser());
      
      ticket = ticketDAO.create(ticket);
      
      System.out.println("buyTicket=======================================");
      System.out.println(ticket);
      System.out.println(this.scheduleItem);
      System.out.println("================================================");
      
      return "my_tickets?faces-redirect=true";
    }
  }
  
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
  
  public Schedule getCurrentSchedule() {
    if(this.getMovie().getSchedules().isEmpty()) {
      return null;
    }
    else {
      return this.movie.getSchedules().get(this.movie.getSchedules().size() - 1);
    }
  }

  public ScheduleItem getScheduleItem() {
    return scheduleItem;
  }
  
  public void setScheduleItem(ScheduleItem scheduleItem) {
    this.scheduleItem = scheduleItem;
  }

  public Converter getScheduleItemConverter() {
    return new ScheduleItemConverter(this.getCurrentSchedule().getScheduleItems());
  }
  
  private HttpServletRequest getRequest() {
    return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }
  
  private Cookie findCookie(String cookieName) {
    Cookie cookie = null;
    Cookie[] cookies = this.getRequest().getCookies();
    for(int i = 0; i < cookies.length; i++) {
      if(cookies[i].getName().equals(cookieName)) {
        cookie = cookies[i];
      }
    }
    return cookie;
  }

}

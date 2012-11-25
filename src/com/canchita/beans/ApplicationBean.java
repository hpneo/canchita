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
@RequestScoped
public class ApplicationBean implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<Movie> lastMovies;
  private User currentUser;

  public List<Movie> getLastMovies() {
    Date currentDate = new Date();
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    Date start_at = calendar.getTime();
    
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    Date end_at = calendar.getTime();
    
    ScheduleDAO scheduleDAO = new ScheduleDAO();
    
    Map<String,Object> parameters = new HashMap<String,Object>();

    parameters.put("start_at", start_at);
    parameters.put("end_at", end_at);

    System.out.println("StartAt: " + start_at);
    System.out.println("EndAt: " + end_at);
    
    List<Schedule> schedules = scheduleDAO.query(parameters);
    
    this.lastMovies = new ArrayList<Movie>();
    
    for(int i = 0; i < schedules.size(); i++) {
      this.lastMovies.add(schedules.get(i).getMovie());
    }
    
    return this.lastMovies;
  }
  public void setLastMovies(List<Movie> lastMovies) {
    this.lastMovies = lastMovies;
  }
  
  public User getCurrentUser() {
    this.currentUser = null;
    if(this.findCookie("current_user") != null) {
      UserDAO userDAO = new UserDAO();
      this.currentUser = userDAO.find(new Integer(this.findCookie("current_user").getValue()).intValue());
    }
    return this.currentUser;
  }
  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
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

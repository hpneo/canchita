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
public class AdminBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private List<Movie> movies;
  private List<User> users;
  
  public List<Movie> getMovies() {
    MovieDAO movieDAO = new MovieDAO();
    this.movies = movieDAO.list();
    return this.movies;
  }

  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }
  
  public List<User> getUsers() {
    UserDAO userDAO = new UserDAO();
    this.users = userDAO.list();
    return this.users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
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

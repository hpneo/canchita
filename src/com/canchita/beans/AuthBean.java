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
public class AuthBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String email;
  private String password;
  private String message;
  private String status = "";
  
  public String auth() {
    UserDAO userDAO = new UserDAO();
    
    Map<String,String> parameters = new HashMap<String,String>();
    parameters.put("email", this.email);
    parameters.put("password", UtilsBean.encryptPassword(this.password));
    
    User user = userDAO.find_by(parameters);
    
    if(user == null) {
      this.message = "Email y/o contraseña incorrectos";
      this.status = "error";
      return null;
    }
    else {
      Cookie cookie = new Cookie("current_user", new Integer(user.getId()).toString());
      this.getResponse().addCookie(cookie);
      
      System.out.println(this.findCookie("current_user"));
      this.message = "Bienvenido " + user.getName();
      this.status = "success";
      return "index?faces-redirect=true";
    }
  }
  
  public String authAdmin() {
    AdminUserDAO adminUserDAO = new AdminUserDAO();
    
    Map<String,String> parameters = new HashMap<String,String>();
    parameters.put("email", this.email);
    parameters.put("password", UtilsBean.encryptPassword(this.password));
    
    AdminUser user = (AdminUser)adminUserDAO.find_by(parameters);
    
    if(user == null) {
      this.message = "Email y/o contraseña incorrectos";
      this.status = "error";
      return null;
    }
    else {
      Cookie cookie = new Cookie("current_admin", new Integer(user.getId()).toString());
      this.getResponse().addCookie(cookie);
      
      System.out.println(this.findCookie("current_admin"));
      this.message = "Bienvenido " + user.getEmail();
      this.status = "success";
      return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/admin/index?faces-redirect=true";
    }
  }
  
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
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
    if (cookies != null) {
      for(int i = 0; i < cookies.length; i++) {
        if(cookies[i].getName().equals(cookieName)) {
          cookie = cookies[i];
        }
      }
    }
    return cookie;
  }
}

package com.canchita.beans;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.apache.commons.codec.binary.Hex;

import com.canchita.dao.*;
import com.canchita.models.*;

import javax.faces.bean.*;
import javax.faces.context.*;
import javax.servlet.http.*;

@ManagedBean
@RequestScoped
public class RegisterBean implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private String email;
  private String password;
  private String message;
  private String status = "";
  
  public String register() {
    UserDAO userDAO = new UserDAO();
    
    User user = new User();
    
    user.setName(this.name);
    user.setEmail(this.email);
    user.setPassword(UtilsBean.encryptPassword(this.password));
    
    User registered_user = userDAO.create(user);
    
    if(registered_user.getId() == 0) {
      this.message = "Datos incorrectos en el registro";
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
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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

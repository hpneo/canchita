package com.canchita.beans;

import java.io.Serializable;
import java.util.*;

import com.canchita.dao.*;
import com.canchita.models.*;

import javax.faces.bean.*;

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
    parameters.put("password", this.password);
    
    User user = userDAO.find_by(parameters);
    
    if(user == null) {
      this.message = "Email y/o contraseña incorrectos";
      this.status = "error";
      return null;
    }
    else {
      this.message = "Bienvenido " + user.getName();
      this.status = "success";
      return "index?faces-redirect=true";
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

}

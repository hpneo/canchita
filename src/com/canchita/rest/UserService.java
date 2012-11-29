package com.canchita.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.canchita.models.*;
import com.canchita.beans.UtilsBean;
import com.canchita.dao.*;
import com.sun.jersey.multipart.FormDataParam;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserService {
  
  @GET
  @Path("{id}")
  public User getUser(@PathParam("id") String id) {
    int userId = new Integer(id).intValue();
    
    UserDAO userDAO = new UserDAO();
    User user = userDAO.find(userId);
    
    return sanitizeUser(user);
  }

  @GET
  @Path("{id}/tickets")
  public List<Ticket> getTickets(@PathParam("id") String id) {
    int userId = new Integer(id).intValue();
    
    UserDAO userDAO = new UserDAO();
    User user = userDAO.find(userId);

    List<Ticket> records = user.getTickets();
    List<Ticket> list = new ArrayList<Ticket>();
    
    for(int i = 0; i < records.size(); i++) {
      Ticket item = new Ticket();
      item = sanitizeTicket(records.get(i));
      
      list.add(item);
    }
    
    return list;
  }
  
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
  public HashMap<String, Object> login(User user) {
    System.out.println("email: " + user.getEmail());
    System.out.println("password: " + user.getPassword());
    
    UserDAO userDAO = new UserDAO();
    
    Map<String,String> parameters = new HashMap<String,String>();
    parameters.put("email", user.getEmail());
    parameters.put("password", UtilsBean.encryptPassword(user.getPassword()));
    
    user = userDAO.find_by(parameters);
    
    System.out.println(user);
    HashMap<String, Object> result = new HashMap<String, Object>();

    if(user == null) {
      result.put("error", "Email o contraseña no coinciden");
    }
    else {
      result.put("token", user.getToken());
    }
    
    return result;
  }
  
  private Ticket sanitizeTicket(Ticket ticket) {
    ticket.setUser(null);
    ticket.setScheduleItem(sanitizeScheduleItem(ticket.getScheduleItem()));
    
    return ticket;
  }
  
  private ScheduleItem sanitizeScheduleItem(ScheduleItem scheduleItem) {
    scheduleItem.setSchedule(null);
    
    return scheduleItem;
  }
  
  private User sanitizeUser(User record) {
    User user = new User();
    
    user.setDni(record.getDni());
    user.setEmail(record.getDni());
    user.setId(record.getId());
    user.setName(record.getName());
    user.setPoints(record.getPoints());
    
    return user;
  }

}

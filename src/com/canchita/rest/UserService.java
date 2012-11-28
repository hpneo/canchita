package com.canchita.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.canchita.models.*;
import com.canchita.dao.*;

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
  
  private Ticket sanitizeTicket(Ticket ticket) {
    ticket.setUser(null);
    ticket.setScheduleItem(sanitizeScheduleItem(ticket.getScheduleItem()));
    
    return ticket;
  }
  
  private ScheduleItem sanitizeScheduleItem(ScheduleItem scheduleItem) {
    scheduleItem.setSchedule(null);
    
    return scheduleItem;
  }
  
  private User sanitizeUser(User user) {
    user.setTickets(null);
    user.setPassword(null);
    
    return user;
  }

}

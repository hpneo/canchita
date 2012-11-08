package com.canchita.beans;

import org.primefaces.model.chart.*;

import java.text.SimpleDateFormat;
import java.util.*;

import com.canchita.models.*;

public class StatsBean {
  
  private CartesianChartModel linearModel;
  private List<Ticket> tickets;

  public StatsBean(List<Ticket> tickets) {
    this.tickets = tickets;
    this.createLinearModel();
  }
  
  public void createLinearModel() {
    this.linearModel = new CartesianChartModel();
    
    LineChartSeries ticketsBought = new LineChartSeries();
    ticketsBought.setLabel("Tickets comprados");
    
    SimpleDateFormat sdf_time = new SimpleDateFormat("EEEE dd/MM/yyyy");
    
    for(int i = 0; i < this.tickets.size(); i++) {
      Ticket ticket = this.tickets.get(i);
      ticketsBought.set(sdf_time.format(ticket.getBought_at()), this.getTicketsByDate(ticket.getBought_at()));
    }
    
    this.linearModel.addSeries(ticketsBought);
  }
  
  private int getTicketsByDate(Date date) {
    int ticketsByDate = 0;
    for(int i = 0; i < this.tickets.size(); i++) {
      Ticket ticket = this.tickets.get(i);
      if(ticket.getBought_at().equals(date)) {
        ticketsByDate += 1;
      }
    }
    
    return ticketsByDate;
  }
  
  private List<Date> mapDate() {
    List<Date> boughtAt = new ArrayList<Date>();
    
    for(int i = 0; i < this.tickets.size(); i++) {
      boughtAt.add(this.tickets.get(i).getBought_at());
    }
    
    return boughtAt;
  }
  
  public Date[] getStatsLimits() {
    Date[] limits = new Date[2];
    
    List<Date> boughAt = this.mapDate();

    limits[0] = Collections.min(boughAt);
    limits[1] = Collections.max(boughAt);
    
    return limits;
  }
  
  public int getDaySpan() {
    Date[] limits = this.getStatsLimits();
    return (int)( (limits[1].getTime() - limits[0].getTime()) / (1000 * 60 * 60 * 24));
  }
  
  public CartesianChartModel getLinearModel() {
    return linearModel;
  }

  public void setLinearModel(CartesianChartModel linearModel) {
    this.linearModel = linearModel;
  }
  
}

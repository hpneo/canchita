package com.canchita.dao;

import java.util.*;
import com.canchita.models.*;

import javax.persistence.*;

public class TicketDAO implements ModelDAO<Ticket> {
  
  private EntityManager em = null;
  
  public TicketDAO() {
    this.em = this.createEntityManager();
  }

  @Override
  public Ticket find(int id) {
    return this.em.find(Ticket.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Ticket> list() {
    return this.em.createQuery("SELECT t FROM TICKET t").getResultList();
  }

  @Override
  public Ticket save(Ticket record) {
    if(record.getId() > 0) {
      return this.create(record);
    }
    else {
      return this.update(record);
    }
  }

  @Override
  public Ticket create(Ticket record) {
    this.em.getTransaction().begin();
    this.em.persist(record);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return record;
  }

  @Override
  public Ticket update(Ticket record) {
    Ticket ticket = this.find(record.getId());
    ticket.setQuantity(record.getQuantity());
    ticket.setScheduleItem(record.getScheduleItem());
    
    this.em.getTransaction().begin();
    this.em.merge(ticket);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return ticket;
  }

  @Override
  public void delete(Ticket record) {
    this.em.getTransaction().begin();
    this.em.remove(record);
    this.em.flush();
    this.em.getTransaction().commit();
  }
  
  private EntityManager createEntityManager() {
    if(this.em == null) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("canchita-pu");
      this.em = emf.createEntityManager();
    }
    
    return this.em;
  }
}

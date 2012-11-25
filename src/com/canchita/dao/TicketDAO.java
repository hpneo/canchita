package com.canchita.dao;

import java.util.*;
import com.canchita.models.*;

import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;

public class TicketDAO implements ModelDAO<Ticket> {
  
  @PersistenceContext
  private EntityManager em = null;
  private UserTransaction transaction = null;
  
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
      return this.update(record);
    }
    else {
      return this.create(record);
    }
  }

  @Override
  public Ticket create(Ticket record) {
    try {
      this.getTransaction().begin();
      this.em.persist(record);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return record;
  }

  @Override
  public Ticket update(Ticket record) {
    Ticket ticket = this.find(record.getId());
    ticket.setQuantity(record.getQuantity());
    ticket.setScheduleItem(record.getScheduleItem());
    
    try {
      this.getTransaction().begin();
      this.em.merge(ticket);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return ticket;
  }

  @Override
  public void delete(Ticket record) {
    try {
      this.getTransaction().begin();
      this.em.remove(record);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private EntityManager createEntityManager() {
    if(this.em == null) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("canchita-pu");
      this.em = emf.createEntityManager();
    }
    
    return this.em;
  }
  
  private UserTransaction getTransaction() {
    if (this.transaction == null) {
      try {
        this.transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    return this.transaction;
  }
}

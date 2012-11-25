package com.canchita.dao;

import java.util.List;
import com.canchita.models.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class ScheduleItemDAO implements ModelDAO<ScheduleItem> {

  private EntityManager em = null;
  private UserTransaction transaction = null;
  
  public ScheduleItemDAO() {
    this.em = this.createEntityManager();
  }
  
  @Override
  public ScheduleItem find(int id) {
    return this.em.find(ScheduleItem.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ScheduleItem> list() {
    return this.em.createQuery("SELECT si FROM SCHEDULE_ITEM si").getResultList();
  }

  @Override
  public ScheduleItem save(ScheduleItem record) {
    if(record.getId() > 0) {
      return this.update(record);
    }
    else {
      return this.create(record);
    }
  }

  @Override
  public ScheduleItem create(ScheduleItem record) {
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
  public ScheduleItem update(ScheduleItem record) {
    ScheduleItem schedule_item = this.find(record.getId());
    schedule_item.setDuration(record.getDuration());
    schedule_item.setPrice(record.getPrice());
    schedule_item.setStart_at(record.getStart_at());
    
    try {
      this.getTransaction().begin();
      this.em.merge(schedule_item);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return schedule_item;
  }

  @Override
  public void delete(ScheduleItem record) {
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

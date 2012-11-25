package com.canchita.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.canchita.models.*;

import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;

public class ScheduleDAO implements ModelDAO<Schedule> {
  
  private EntityManager em = null;
  private UserTransaction transaction = null;
  
  public ScheduleDAO() {
    this.em = this.createEntityManager();
  }

  @Override
  public Schedule find(int id) {
    return this.em.find(Schedule.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Schedule> list() {
    return this.em.createQuery("SELECT s FROM SCHEDULE s").getResultList();
  }

  @Override
  public Schedule save(Schedule record) {
    if(record.getId() > 0) {
      return this.update(record);
    }
    else {
      return this.create(record);
    }
  }

  @Override
  public Schedule create(Schedule record) {
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
  public Schedule update(Schedule record) {
    Schedule schedule = this.find(record.getId());
    schedule.setMovie(record.getMovie());
    schedule.setStart_at(record.getStart_at());
    schedule.setEnd_at(record.getEnd_at());
    schedule.setScheduleItems(record.getScheduleItems());
    
    try {
      this.getTransaction().begin();
      this.em.merge(schedule);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return schedule;
  }

  @Override
  public void delete(Schedule record) {
    try {
      this.getTransaction().begin();
      this.em.remove(record);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public List<Schedule> query(Map<String,Object> attributes) {
    String queryString = "SELECT s FROM SCHEDULE s";
    
    Set<Entry<String,Object>> parameters = attributes.entrySet();    
    Iterator<Entry<String,Object>> iterator = parameters.iterator();
    
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    while(iterator.hasNext()) {
      Entry<String,Object> parameter = iterator.next();
      
      if (first)
        first = false;
      else
        sb.append(" AND ");
      
      sb.append("s." + parameter.getKey() + " = :s" + parameter.getKey());
    }
    
    queryString += " WHERE " + sb.toString();
    
    System.out.println(queryString);
    
    Query query = this.em.createQuery(queryString);
    
    iterator = parameters.iterator();
    
    while(iterator.hasNext()) {
      Entry<String,Object> parameter = iterator.next();
      query.setParameter("s" + parameter.getKey(), parameter.getValue());
    }
    
    List<Schedule> results = query.getResultList();
    
    return results;
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

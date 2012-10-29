package com.canchita.dao;

import java.util.List;
import com.canchita.models.*;

import javax.persistence.*;

public class ScheduleDAO implements ModelDAO<Schedule> {
  
  private EntityManager em = null;
  
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
      return this.create(record);
    }
    else {
      return this.update(record);
    }
  }

  @Override
  public Schedule create(Schedule record) {
    this.em.getTransaction().begin();
    this.em.persist(record);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return record;
  }

  @Override
  public Schedule update(Schedule record) {
    Schedule schedule = this.find(record.getId());
    schedule.setMovie(record.getMovie());
    schedule.setSchedule_items(record.getScheduleItems());
    
    this.em.getTransaction().begin();
    this.em.merge(schedule);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return schedule;
  }

  @Override
  public void delete(Schedule record) {
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

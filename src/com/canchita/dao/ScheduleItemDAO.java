package com.canchita.dao;

import java.util.List;
import com.canchita.models.*;

import javax.persistence.*;

public class ScheduleItemDAO implements ModelDAO<ScheduleItem> {

  private EntityManager em = null;
  
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
    this.em.getTransaction().begin();
    this.em.persist(record);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return record;
  }

  @Override
  public ScheduleItem update(ScheduleItem record) {
    ScheduleItem schedule_item = new ScheduleItem();
    schedule_item.setDuration(record.getDuration());
    schedule_item.setPrice(record.getPrice());
    schedule_item.setStart_at(record.getStart_at());
    
    this.em.getTransaction().begin();
    this.em.merge(schedule_item);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return schedule_item;
  }

  @Override
  public void delete(ScheduleItem record) {
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

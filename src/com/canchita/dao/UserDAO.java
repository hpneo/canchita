package com.canchita.dao;

import java.util.List;
import javax.persistence.*;

import com.canchita.models.*;

public class UserDAO implements ModelDAO<User> {
  
  private EntityManager em = null;
  
  public UserDAO() {
    this.em = this.createEntityManager();
  }

  @Override
  public User find(int id) {
    return this.em.find(User.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<User> list() {
    return this.em.createQuery("SELECT u FROM USER u").getResultList();
  }

  @Override
  public User save(User record) {
    if(record.getId() > 0) {
      return this.create(record);
    }
    else {
      return this.update(record);
    }
  }

  @Override
  public User create(User record) {
    this.em.getTransaction().begin();
    this.em.persist(record);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return record;
  }

  @Override
  public User update(User record) {
    User user = this.find(record.getId());
    user.setDni(record.getDni());
    user.setEmail(record.getEmail());
    user.setName(record.getName());
    user.setPassword(record.getPassword());
    user.setPoints(record.getPoints());
    
    this.em.getTransaction().begin();
    this.em.merge(user);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return user;
  }

  @Override
  public void delete(User record) {
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

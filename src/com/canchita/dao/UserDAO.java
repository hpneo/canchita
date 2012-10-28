package com.canchita.dao;

import java.util.*;
import java.util.Map.*;
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
  
  public User find_by(Map<String,String> attributes) {
    String queryString = "SELECT u FROM USER u";
    
    Set<Entry<String,String>> parameters = attributes.entrySet();    
    Iterator<Entry<String,String>> iterator = parameters.iterator();
    
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    while(iterator.hasNext()) {
      Entry<String,String> parameter = iterator.next();
      
      if (first)
        first = false;
      else
        sb.append(" AND ");
      
      sb.append("u." + parameter.getKey() + " = :u" + parameter.getKey());
    }
    
    queryString += " WHERE " + sb.toString();
    
    Query query = this.em.createQuery(queryString);
    
    iterator = parameters.iterator();
    
    while(iterator.hasNext()) {
      Entry<String,String> parameter = iterator.next();
      query.setParameter("u" + parameter.getKey(), parameter.getValue());
    }
    
    List<User> results = query.getResultList();
    User record = null;
    
    if(!results.isEmpty()) {
      record = results.get(0);
    }
    
    return record;
  }
  
  private EntityManager createEntityManager() {
    if(this.em == null) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("canchita-pu");
      this.em = emf.createEntityManager();
    }
    
    return this.em;
  }

}

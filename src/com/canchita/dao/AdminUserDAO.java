package com.canchita.dao;

import java.util.*;
import java.util.Map.*;
import javax.persistence.*;

import com.canchita.models.*;

public class AdminUserDAO implements ModelDAO<AdminUser> {
  
  @PersistenceContext
  private EntityManager em = null;
  
  public AdminUserDAO() {
    this.em = this.createEntityManager();
  }

  @Override
  public AdminUser find(int id) {
    return this.em.find(AdminUser.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AdminUser> list() {
    return this.em.createQuery("SELECT a FROM ADMIN_USER a").getResultList();
  }

  @Override
  public AdminUser save(AdminUser record) {
    if(record.getId() > 0) {
      return this.update(record);
    }
    else {
      return this.create(record);
    }
  }

  @Override
  public AdminUser create(AdminUser record) {
    System.out.println("AdminUser#create");
    this.em.getTransaction().begin();
    this.em.persist(record);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return record;
  }

  @Override
  public AdminUser update(AdminUser record) {
    System.out.println("AdminUser#update");
    AdminUser adminUser = this.find(record.getId());
    adminUser.setEmail(record.getEmail());
    adminUser.setPassword(record.getPassword());
    
    this.em.getTransaction().begin();
    this.em.merge(adminUser);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return adminUser;
  }

  @Override
  public void delete(AdminUser record) {
    this.em.getTransaction().begin();
    this.em.remove(record);
    this.em.flush();
    this.em.getTransaction().commit();
  }
  
  public AdminUser find_by(Map<String,String> attributes) {
    String queryString = "SELECT a FROM ADMIN_USER a";
    
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
      
      sb.append("a." + parameter.getKey() + " = :a" + parameter.getKey());
    }
    
    queryString += " WHERE " + sb.toString();
    
    Query query = this.em.createQuery(queryString);
    
    iterator = parameters.iterator();
    
    while(iterator.hasNext()) {
      Entry<String,String> parameter = iterator.next();
      query.setParameter("a" + parameter.getKey(), parameter.getValue());
    }
    
    List<AdminUser> results = query.getResultList();
    AdminUser record = null;
    
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

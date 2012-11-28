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

public class CommentDAO implements ModelDAO<Comment> {

  private EntityManager em = null;
  private UserTransaction transaction = null;
  
  public CommentDAO() {
    this.em = this.createEntityManager();
  }
  
  @Override
  public Comment find(int id) {
    return this.em.find(Comment.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Comment> list() {
    return this.em.createQuery("SELECT c FROM COMMENT c").getResultList();
  }
  
  @Override
  public Comment save(Comment record) {
    if(record.getId() > 0) {
      return this.update(record);
    }
    else {
      return this.create(record);
    }
  }
  
  @Override
  public void delete(Comment record) {
    try {
      this.getTransaction().begin();
      this.em.remove(record);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public Comment create(Comment record) {
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
  public Comment update(Comment record) {
    Comment comment = this.find(record.getId());
    comment.setMovie(record.getMovie());
    comment.setText(record.getText());
    comment.setUser(record.getUser());
    
    try {
      this.getTransaction().begin();
      this.em.merge(comment);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return comment;
  }
  
  public List<Comment> query(Map<String,Object> attributes) {
    String queryString = "SELECT c FROM COMMENT c";
    
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
      
      sb.append("c." + parameter.getKey() + " = :c" + parameter.getKey());
    }
    
    queryString += " WHERE " + sb.toString();
    
    System.out.println(queryString);
    
    Query query = this.em.createQuery(queryString);
    
    iterator = parameters.iterator();
    
    while(iterator.hasNext()) {
      Entry<String,Object> parameter = iterator.next();
      query.setParameter("c" + parameter.getKey(), parameter.getValue());
    }
    
    List<Comment> results = query.getResultList();
    
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

package com.canchita.dao;

import java.util.*;
import com.canchita.models.*;

import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;

public class GenreDAO implements ModelDAO<Genre> {

  private EntityManager em = null;
  private UserTransaction transaction = null;
  
  public GenreDAO() {
    this.em = this.createEntityManager();
  }
  
  @Override
  public Genre find(int id) {
    return this.em.find(Genre.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Genre> list() {
    return this.em.createQuery("SELECT g FROM GENRE g").getResultList();
  }

  @Override
  public Genre save(Genre record) {
    if(record.getId() > 0) {
      return this.update(record);
    }
    else {
      return this.create(record);
    }
  }
  
  @Override
  public Genre create(Genre record) {
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
  public Genre update(Genre record) {
    Genre movie = this.find(record.getId());
    movie.setName(record.getName());
    
    try {
      this.getTransaction().begin();
      this.em.merge(movie);
      this.em.flush();
      this.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return movie;
  }
  
  @Override
  public void delete(Genre record) {
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

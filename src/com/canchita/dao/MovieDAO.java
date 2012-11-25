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

public class MovieDAO implements ModelDAO<Movie> {

  private EntityManager em = null;
  private UserTransaction transaction = null;
  
  public MovieDAO() {
    this.em = this.createEntityManager();
  }
  
  @Override
  public Movie find(int id) {
    return this.em.find(Movie.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Movie> list() {
    return this.em.createQuery("SELECT m FROM MOVIE m").getResultList();
  }
  
  @Override
  public Movie save(Movie record) {
    if(record.getId() > 0) {
      return this.update(record);
    }
    else {
      return this.create(record);
    }
  }
  
  @Override
  public void delete(Movie record) {
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
  public Movie create(Movie record) {
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
  public Movie update(Movie record) {
    Movie movie = this.find(record.getId());
    movie.setName(record.getName());
    movie.setDescription(record.getDescription());
    movie.setGenre(record.getGenre());
    movie.setPoster(record.getPoster());
    movie.setRating(record.getRating());
    
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
  
  public List<Movie> query(Map<String,Object> attributes) {
    String queryString = "SELECT m FROM MOVIE m";
    
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
      
      sb.append("m." + parameter.getKey() + " = :m" + parameter.getKey());
    }
    
    queryString += " WHERE " + sb.toString();
    
    System.out.println(queryString);
    
    Query query = this.em.createQuery(queryString);
    
    iterator = parameters.iterator();
    
    while(iterator.hasNext()) {
      Entry<String,Object> parameter = iterator.next();
      query.setParameter("m" + parameter.getKey(), parameter.getValue());
    }
    
    List<Movie> results = query.getResultList();
    
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

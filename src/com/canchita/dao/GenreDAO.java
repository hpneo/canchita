package com.canchita.dao;

import java.util.*;
import com.canchita.models.*;

import javax.persistence.*;

public class GenreDAO implements ModelDAO<Genre> {

  private EntityManager em = null;
  
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
      return this.create(record);
    }
    else {
      return this.update(record);
    }
  }
  
  @Override
  public Genre create(Genre record) {
    this.em.getTransaction().begin();
    this.em.persist(record);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return record;
  }
  
  @Override
  public Genre update(Genre record) {
    Genre movie = this.find(record.getId());
    movie.setName(record.getName());
    
    this.em.getTransaction().begin();
    this.em.merge(movie);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return movie;
  }
  
  @Override
  public void delete(Genre record) {
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

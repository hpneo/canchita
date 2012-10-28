package com.canchita.dao;

import java.util.List;
import com.canchita.models.*;

import javax.persistence.*;

public class MovieDAO implements ModelDAO<Movie> {

  private EntityManager em = null;
  
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
      return this.create(record);
    }
    else {
      return this.update(record);
    }
  }
  
  @Override
  public void delete(Movie record) {
    this.em.getTransaction().begin();
    this.em.remove(record);
    this.em.flush();
    this.em.getTransaction().commit();
  }
  
  @Override
  public Movie create(Movie record) {
    this.em.getTransaction().begin();
    this.em.persist(record);
    this.em.flush();
    this.em.getTransaction().commit();
    
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
    
    this.em.getTransaction().begin();
    this.em.merge(movie);
    this.em.flush();
    this.em.getTransaction().commit();
    
    return movie;
  }
  
  private EntityManager createEntityManager() {
    if(this.em == null) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("canchita-pu");
      this.em = emf.createEntityManager();
    }
    
    return this.em;
  }
}

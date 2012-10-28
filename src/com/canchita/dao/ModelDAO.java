package com.canchita.dao;

import java.util.*;

public interface ModelDAO<T> {

  public abstract T find(int id);
  
  public abstract List<T> list();

  public abstract T save(T record);

  public abstract T create(T record);

  public abstract T update(T record);

  public abstract void delete(T record);

}
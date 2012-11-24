package com.canchita.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.*;

import com.canchita.models.*;

public class MovieConverter implements Converter {
  
  private List<Movie> movies;
  
  public MovieConverter(List<Movie> movies) {
    if (movies != null) {
      this.movies = movies;
    }
    else {
      this.movies = new ArrayList<Movie>();
    }
  }

  @Override
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
    int id = Integer.valueOf(arg2);
    
    for(int i = 0; i < this.movies.size(); i++) {
      if(this.movies.get(i).getId() == id) {
        return this.movies.get(i);
      }
    }
    
    return null;
  }

  @Override
  public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
    Movie genre = (Movie)arg2;
    return String.valueOf(genre.getId());
  }

}

package com.canchita.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.*;

import com.canchita.models.*;

public class GenreConverter implements Converter {
  
  private List<Genre> genres;
  
  public GenreConverter(List<Genre> genres) {
    if (genres != null) {
      this.genres = genres;
    }
    else {
      this.genres = new ArrayList<Genre>();
    }
  }

  @Override
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
    int id = Integer.valueOf(arg2);
    
    for(int i = 0; i < this.genres.size(); i++) {
      if(this.genres.get(i).getId() == id) {
        return this.genres.get(i);
      }
    }
    
    return null;
  }

  @Override
  public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
    Genre genre = (Genre)arg2;
    return String.valueOf(genre.getId());
  }

}

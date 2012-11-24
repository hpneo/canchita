package com.canchita.converters;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.canchita.models.Schedule;

public class ScheduleConverter implements Converter {
  
  private List<Schedule> schedules;
  
  public ScheduleConverter(List<Schedule> schedules) {
    if(schedules != null) {
      this.schedules = schedules;
    }
    else {
      this.schedules = new ArrayList<Schedule>();
    }
  }

  @Override
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
    int id = Integer.valueOf(arg2);
    
    for(int i = 0; i < this.schedules.size(); i++) {
      if(this.schedules.get(i).getId() == id) {
        return this.schedules.get(i);
      }
    }
    
    return null;
  }

  @Override
  public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
    Schedule schedule = (Schedule)arg2;
    return String.valueOf(schedule.getId());
  }

}

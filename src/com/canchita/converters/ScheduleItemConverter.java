package com.canchita.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.canchita.models.ScheduleItem;

public class ScheduleItemConverter implements Converter {
  
  private List<ScheduleItem> scheduleItems;
  
  public ScheduleItemConverter(List<ScheduleItem> scheduleItems) {
    this.scheduleItems = scheduleItems;
  }

  @Override
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
    int id = Integer.valueOf(arg2);
    
    for(int i = 0; i < this.scheduleItems.size(); i++) {
      if(this.scheduleItems.get(i).getId() == id) {
        return this.scheduleItems.get(i);
      }
    }
    
    return null;
  }

  @Override
  public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
    ScheduleItem scheduleItem = (ScheduleItem)arg2;
    return String.valueOf(scheduleItem.getId());
  }

}

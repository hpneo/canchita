package com.canchita.beans;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class PhaseListener implements javax.faces.event.PhaseListener {

  @Override
  public void afterPhase(PhaseEvent event) {
    //
  }

  @Override
  public void beforePhase(PhaseEvent event) {
    String path = event.getFacesContext().getExternalContext().getRequestServletPath();
    String contextPath = event.getFacesContext().getExternalContext().getRequestContextPath();
    
    Cookie adminCookie = this.findCookie(event.getFacesContext().getExternalContext(), "current_admin");
    
    if (path.contains("/admin") && !path.contains("/admin/auth")) {
      try {
        System.out.println("===================================");
        System.out.println(adminCookie);
        System.out.println("===================================");
        if (adminCookie == null) {
          event.getFacesContext().getExternalContext().redirect(contextPath + "/admin/auth.xhtml");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public PhaseId getPhaseId() {
    return PhaseId.RENDER_RESPONSE;
  }
  
  private HttpServletRequest getRequest(ExternalContext context) {
    return (HttpServletRequest) context.getRequest();
  }
  
  private Cookie findCookie(ExternalContext context, String cookieName) {
    Cookie cookie = null;
    Cookie[] cookies = this.getRequest(context).getCookies();
    if(cookies != null) {
      for(int i = 0; i < cookies.length; i++) {
        if(cookies[i].getName().equals(cookieName)) {
          cookie = cookies[i];
        }
      }
    }
    return cookie;
  }

}

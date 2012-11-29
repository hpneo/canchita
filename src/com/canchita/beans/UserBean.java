package com.canchita.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import com.canchita.dao.*;
import com.canchita.models.*;
import com.canchita.converters.*;

import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.convert.Converter;
import javax.servlet.http.*;

@ManagedBean
@RequestScoped
public class UserBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @ManagedProperty("#{param.id}")
  private int id;

  public User getUser() {
    UserDAO userDAO = new UserDAO();
    return userDAO.find(this.id);
  }
  
  public String getUserAvatar() {
    User user = this.getUser();
    String hash = UserBean.md5Hex(user.getEmail());
    
    String avatar = "http://www.gravatar.com/avatar/" + hash;
    
    try {
      avatar += "?d=" + URLEncoder.encode("http://i.imgur.com/lowbA.png", "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    
    return avatar;
  }
  
  private static String hex(byte[] array) {
    StringBuffer sb = new StringBuffer();
    
    for (int i = 0; i < array.length; ++i) {
      sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));        
    }
    
    return sb.toString();
  }
  public static String md5Hex (String message) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      return hex (md.digest(message.getBytes("CP1252")));
    } catch (NoSuchAlgorithmException e) {
    } catch (UnsupportedEncodingException e) {
    }
    return null;
  }
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}

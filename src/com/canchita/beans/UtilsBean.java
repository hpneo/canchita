package com.canchita.beans;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.faces.bean.ManagedBean;

import org.apache.commons.codec.binary.Hex;

import com.canchita.models.Genre;
import com.canchita.dao.GenreDAO;

@ManagedBean
public class UtilsBean {
  
  public static String encryptPassword(String password) {
    String output = "";
    
    try {
      byte[] textBytes = password.getBytes(Charset.forName("UTF8"));
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(textBytes);
      byte[] codigo = md.digest();
      output = new String(Hex.encodeHex(codigo));

    } catch (NoSuchAlgorithmException ex) {
    }
    return output;
  }
  
  public List<Genre> getGenres() {
    GenreDAO genreDAO = new GenreDAO();
    return genreDAO.list();
  }

}

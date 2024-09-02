package com.example.vertxweb.services;


import com.example.vertxweb.dao.AuthDAO;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

  AuthDAO authDAO;
  public AuthService()
  {
    authDAO = new AuthDAO();
  }
  public String register(String username, String password,String email)
  {
      var answer = authDAO.create(username,password,email);
      return answer?"User Created":"Username already present";
  }
  public boolean login(String username,String password)
  {
    return authDAO.login(username,password);
  }

}


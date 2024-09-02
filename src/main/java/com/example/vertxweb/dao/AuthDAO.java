package com.example.vertxweb.dao;

import com.example.vertxweb.models.User;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthDAO
{
  Map<String, User> users;
  static long totalUsers;
  public AuthDAO()
  {
    users = new LinkedHashMap<>();
    totalUsers = 0;
  }

  public boolean login(String username,String password)
  {
    System.out.println("In AUTH DAO "+users);
    var user = users.get(username.toLowerCase());
    if(user==null)
      return false;
    System.out.println("Users password "+user.getPassword());
    return user.getPassword().equals(password);
  }

  public boolean create(String username,String password,String email)
  {

    if(users.get(username.toLowerCase())==null)
    {
      totalUsers++;
      users.put(username.toLowerCase(),new User(totalUsers,username,password,email));
      return true;
    }
    return false;
  }

}

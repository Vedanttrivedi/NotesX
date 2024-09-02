package com.example.vertxweb.services;

import com.example.vertxweb.dao.TodoDAO;
import com.example.vertxweb.models.Todo;
import io.vertx.core.json.JsonArray;

import java.time.LocalDateTime;

public class TodoService
{
  TodoDAO todoDAO;
  public TodoService()
  {
    todoDAO = new TodoDAO();
  }
  public String delete(String id)
  {
    var answer = todoDAO.delete(Long.parseLong(id));
    return answer?"Record Deleted":"Record Not Found!";
  }
  public String create(String title)
  {
    LocalDateTime time = LocalDateTime.now();
    todoDAO.create(title,time.toString());
    return time.toString();
  }

  public String put(String id ,String title,String status)
  {
    LocalDateTime time = LocalDateTime.now();
    boolean flag = status.toLowerCase().startsWith("t");
    var isUpdated = todoDAO.put(Long.parseLong(id),title,flag,time.toString());
    return isUpdated?"Updated At "+time.toString():"Record Not Found";
  }



  public JsonArray getTodos()
  {
    System.out.println("Request Arrived in Service "+todoDAO);
    return todoDAO.getTodos();
  }
}

package com.example.vertxweb.dao;

import com.example.vertxweb.models.Todo;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public class TodoDAO
{

  private JsonArray todos;
  private static long id;
  public TodoDAO()
  {
    todos = new JsonArray();
    id=0;
  }

  public boolean put(long id,String title,boolean status,String time)
  {
    for (int i=0;i<todos.size() ;i++)
    {
        var data = todos.getJsonObject((int)id);
        if(data.getLong("id").equals(id))
        {
          //update the json object
          data.put("task",title);
          data.put("status",status);
          data.put("time",time);
          System.out.println("Found At DAO!!!!!");
          return true;
        }
    }
    System.out.println("Did not found with this id "+id);
    return false;
  }
  public void create(String title,String time)
  {
    id++;
    todos.add(new Todo(id,title,time).toJson());
    System.out.println("Data Added In DAO!!");
  }

  public JsonArray getTodos()
  {
    System.out.println("Total DAO  Todos "+todos);
    return todos;
  }
  public boolean delete(long id)
  {
    for (int i=0;i<todos.size() ;i++)
    {
      var data = todos.getJsonObject((int)id);
      todos.remove(data);
      return true;
    }
    return false;
  }
}

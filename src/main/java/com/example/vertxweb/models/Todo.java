package com.example.vertxweb.models;
import io.vertx.core.json.JsonObject;

public class Todo
{

  private long id;
  private String task;
  private boolean done;
  private String time;
  public Todo(long id,String task, String time)
  {
    this.id = id;
    this.task = task;
    this.done = false;
    this.time =time;
  }

  public long getId() {
    return id;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public JsonObject toJson() {
    return new JsonObject()
      .put("id",id)
      .put("task", task)
      .put("done", done)
      .put("time", time)
      ;
  }
}


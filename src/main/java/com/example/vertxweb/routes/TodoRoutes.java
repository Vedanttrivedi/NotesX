package com.example.vertxweb.routes;

import com.example.vertxweb.services.TodoService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

public class TodoRoutes
{
  TodoService todoService;
  Vertx vertx;
  public TodoRoutes(Vertx vertx)
  {
    todoService = new TodoService();
    this.vertx=vertx;
  }

  public  void attach(Router routes)
  {
    routes.post("/create").

      handler(BodyHandler.create()).
      handler(context->
      {
          var title = context.request().getFormAttribute("title");
        System.out.println("Title in the routes "+ title);
          var time = todoService.create(title);
          context.response().end("<h1>Todo Added At "+time+"</h1>");
      });

    routes.get("/todos").handler(context->

    {
        var todos = todoService.getTodos();
        context.response().end("<h1>"+todos+"</h1>");
    });



    routes.delete("/delete/:id")

    .handler(TimeoutHandler.create(4000))
      .handler(context->{
        var id = context.pathParam("id");
        vertx.executeBlocking(
          event->{
          var answer = todoService.delete("1");
          event.complete(answer);
        },eventFuture->
          {
            context.response().end(eventFuture.result().toString());
          });
      }).failureHandler(handler->{
        handler.response().end("Record Not Found With That ID..Request Timed Out");
      });


    routes.put("/put/:id")

      .handler(TimeoutHandler.create(4000))
      .handler(BodyHandler.create())
      .handler(context->{
        var id = context.pathParam("id");
        var title = context.request().getFormAttribute("title");
        var status = context.request().getFormAttribute("status");

        vertx.executeBlocking(
          handler->{
          var updatedTime = todoService.put(id,title,status);
          handler.complete(updatedTime);
        },res->{
            if(res.succeeded())
            {
              context.response().end("<h1>"+ res.result().toString()+"</h1>");
            }
        });

        System.out.println("Request In Router Put "+id+"\t"+title+"\t"+status);
      }).
      failureHandler(errorHandler->{
        errorHandler.response().end("Request TimeOut");
      });

  }
}

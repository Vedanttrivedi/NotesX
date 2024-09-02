package com.example.vertxweb.routes;

import com.example.vertxweb.services.AuthService;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class AuthRoutes
{
  Vertx vertx;
  AuthService authService;
  JWTAuth jwtAuth;
  public AuthRoutes(Vertx vertx,JWTAuth jwtAuth)
  {
    this.vertx = vertx;
    authService =new AuthService();
    this.jwtAuth = jwtAuth;
  }
  public void attach(Router router)
  {
    router.post("/user/register")

      .handler(BodyHandler.create())
      .handler(context->{
        var username = context.request().getFormAttribute("username");
        var password = context.request().getFormAttribute("password");
        var email = context.request().getFormAttribute("email");
        System.out.println("/user/create called "+username+"\t"+password+"\t"+email);
        var answer = authService.register(username,password,email);
        context.response().end(answer);
      });

    router.post("/user/login")

      .handler(BodyHandler.create())
      .handler(context->{
        var username = context.request().getFormAttribute("username");
        var password = context.request().getFormAttribute("password");
        System.out.println("/user/login called "+username+"\t"+password);
        var answer = authService.login(username,password);
        //if answer is true then generate JWT Token and send this to user
        //also set this as cookie
        if(answer)
        {

          var token = jwtAuth.generateToken(
            new JsonObject().put("username",username),new JWTOptions().setExpiresInMinutes(60));

          System.out.println("Generated Token "+token);

          context.response().
            addCookie(Cookie.cookie("jwt",token))
            .setStatusCode(200)
            .end(token);
        }
        else
        {
          context.response().end("Invalid Credential");
        }
      });

  }

}

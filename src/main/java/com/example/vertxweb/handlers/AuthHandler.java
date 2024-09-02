//package com.example.vertxweb.handlers;
//
//import com.example.vertxweb.services.AuthService;
//import io.vertx.core.json.JsonObject;
//import io.vertx.ext.auth.KeyStoreOptions;
//import io.vertx.ext.auth.jwt.JWTAuth;
//import io.vertx.ext.auth.JWTOptions;
//import io.vertx.ext.auth.jwt.JWTAuthOptions;
//import io.vertx.ext.web.RoutingContext;
//
//public class AuthHandler {
//
//  private final AuthService authService;
//  private final JWTAuth jwtAuth;
//
//  public AuthHandler(JWTAuth jwtAuth) {
//    this.jwtAuth=jwtAuth;
//    this.authService = new AuthService();
//  }
//
//
//  // Handler for user registration
//  public void handleRegister(RoutingContext context)
//  {
//    String username =context.request().getFormAttribute("username");
//    String password = context.request().getFormAttribute("password");
//    System.out.println("Register Called "+username+"\t"+password);
//    long timeoutId = context.vertx().setTimer(5000, id -> {
//      if (!context.response().ended()) {
//        context.response()
//          .setStatusCode(408)
//          .putHeader("Content-Type", "application/json")
//          .end(new JsonObject().put("message", "Request timed out").encode());
//      }
//    });
//    if (authService.registerUser(username.toLowerCase(), password)) {
//      context.response()
//        .setStatusCode(201)
//        .putHeader("Content-Type", "application/json")
//        .end(new JsonObject().put("message", "User registered successfully").encode());
//    } else {
//      context.response()
//        .setStatusCode(400)
//        .putHeader("Content-Type", "application/json")
//        .end(new JsonObject().put("message", "User already exists").encode());
//    }
//  }
//
//  public void handleLogin(RoutingContext context)
//  {
//
//    String username = context.request().getFormAttribute("username").toLowerCase();
//    String password = context.request().getFormAttribute("password");
//    System.out.println("Login Data "+username+"\t"+password);
//    if (authService.authenticateUser(username, password))
//    {
//      String token = jwtAuth.generateToken(
//        new JsonObject().put("sub", username), // Subject claim in JWT
//        new JWTOptions().setExpiresInMinutes(60) // Token expiry
//      );
//      System.out.println("Jwt Token "+token);
//
//      context.response()
//        .putHeader("Content-Type", "application/json")
//        .end(new JsonObject().put("token", token).encode());
//    }
//    else
//    {
//      context.response()
//        .setStatusCode(401)
//        .putHeader("Content-Type", "application/json")
//        .end(new JsonObject().put("message", "Invalid credentials").encode());
//    }
//  }
//
//  public void handleProtectedRoute(RoutingContext context)
//  {
//    System.out.println("Arrived On Protected "+context.user());
//    context.user().isAuthorized("authenticated", res -> {
//      if (res.succeeded() && res.result()) {
//        // Authorized
//        context.response()
//          .putHeader("Content-Type", "application/json")
//          .end(new JsonObject().put("message", "Access granted to protected resource").encode());
//      } else {
//        // Not authorized
//        context.response()
//          .setStatusCode(403)
//          .putHeader("Content-Type", "application/json")
//          .end(new JsonObject().put("message", "Access denied").encode());
//      }
//    });
//  }
//}

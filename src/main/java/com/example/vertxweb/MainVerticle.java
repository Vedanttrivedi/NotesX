package com.example.vertxweb;

import com.example.vertxweb.routes.AuthRoutes;
import com.example.vertxweb.routes.TodoRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.jwt.authorization.JWTAuthorization;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.TimeoutHandler;

public class MainVerticle extends AbstractVerticle {

  public void start(Promise promise)
  {
    JWTAuthOptions jwtAuthOptions = new JWTAuthOptions();
    JWTAuth authProvider = JWTAuth.create(vertx, jwtAuthOptions);

    System.out.println("JWT passed");

    Router router = Router.router(vertx);

    TodoRoutes todoRoutes = new TodoRoutes(vertx);
    AuthRoutes authRoutes = new AuthRoutes(vertx,authProvider);
    todoRoutes.attach(router);
    authRoutes.attach(router);

    router.route("/protected/*").handler(JWTAuthHandler.create(authProvider));

    router.get("/protected/somepage").
    handler(TimeoutHandler.create(4000)).
      failureHandler(errorHandler->{
        System.out.println("Error "+errorHandler.body());
        errorHandler.response().end("Authorization Error.Request TimedOut");
      }).
      handler(ctx -> {
      // some handle code...
      System.out.println("Visited this page "+ctx.user());
    });

    vertx.createHttpServer().requestHandler(router).

      exceptionHandler(errorHandler->{
        System.out.println("ERROR IN SERVER "+errorHandler.getMessage());
      }).
      listen(8888, http ->
      {
      if (http.succeeded())
      {
        System.out.println("HTTP server started on port 8888");
      }
      else
      {
        System.out.println("Failed");
      }
    });
  }
}

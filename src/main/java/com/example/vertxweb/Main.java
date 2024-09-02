package com.example.vertxweb;

import io.vertx.core.Vertx;

public class Main
{
  public static void main(String[] args)
  {
    var vertx = Vertx.vertx();

    vertx.deployVerticle(new MainVerticle(),error->
    {
      if(error.failed())
      {
        System.out.println("Cause "+error.result());
      }
      else
      {
        System.out.println("Verticle Deployed");
      }
    });
  }
}

package com.example.demo.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.http.ContentType.JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =
    SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerIT {
  @LocalServerPort
  int port;

  @Before
  public void setUpBase() {
    RestAssured.port = port;
    RestAssured.basePath = "/api";
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    RestAssured.requestSpecification = new RequestSpecBuilder().build()
        .accept(JSON).contentType(JSON)
        .log().method()
        .log().uri()
        .log().headers()
        .log().cookies();
  }


}

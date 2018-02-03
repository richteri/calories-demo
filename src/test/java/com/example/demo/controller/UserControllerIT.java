package com.example.demo.controller;

import com.example.demo.domain.Meal;
import com.example.demo.domain.MealBuilder;
import com.example.demo.domain.User;
import com.example.demo.domain.UserBuilder;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserControllerIT extends BaseControllerIT {

  @Test
  public void findAll_managerOk() {
    User[] users = given().auth().basic("manager", "manager")
        .when().get("/users")
        .then().statusCode(HttpStatus.OK.value())
        .extract().body().as(User[].class);
    assertNotNull(users);
  }

  @Test
  public void findAll_userForbidden() {
    given().auth().basic("user01", "test")
        .when().get("/users")
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void findOne_managerOk() {
    User user = given().auth().basic("manager", "manager")
        .when().get("/users/1")
        .then().statusCode(HttpStatus.OK.value())
        .extract().body().as(User.class);
    assertNotNull(user);
  }

  @Test
  public void findOne_userForbidden() {
    given().auth().basic("user01", "test")
        .when().get("/users/1")
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }


  @Test
  public void findOne_userSelfOk() {
    User user = given().auth().basic("user01", "test")
        .when().get("/users/3")
        .then().statusCode(HttpStatus.OK.value())
        .extract().body().as(User.class);
    assertNotNull(user);
  }

  @Test
  public void findOne_userSelfMealsOk() {
    Meal[] meals = given().auth().basic("user01", "test")
        .when().get("/users/3/meals")
        .then().statusCode(HttpStatus.OK.value())
        .extract().body().as(Meal[].class);
    assertNotNull(meals);
  }

  @Test
  public void findOne_userMealsForbidden() {
    given().auth().basic("user01", "test")
        .when().get("/users/4/meals")
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void create_missingUsername() {
    User user = UserBuilder.anUser()
        .withName("test")
        .withPassword("test")
        .withCalories(2000)
        .build();

    given()
        .auth().basic("user01", "test")
        .body(user)
        .when().post("/users")
        .then().statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void create_invalidUsername() {
    User user = UserBuilder.anUser()
        .withName("test")
        .withUsername("test test")
        .withPassword("test")
        .withCalories(2000)
        .build();

    given()
        .auth().basic("user01", "test")
        .body(user)
        .when().post("/users")
        .then().statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void create_userForbidden() {
    User user = UserBuilder.anUser()
        .withName("test")
        .withUsername("test")
        .withPassword("test")
        .withCalories(2000)
        .build();

    given()
        .auth().basic("user01", "test")
        .body(user)
        .when().post("/users")
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void create_managerOk() {
    User user = UserBuilder.anUser()
        .withName("test")
        .withUsername("test")
        .withPassword("test")
        .withCalories(2000)
        .build();

    String sId = given()
        .auth().basic("manager", "manager")
        .body(user)
        .when().post("/users")
        .then().statusCode(HttpStatus.CREATED.value())
        .extract().header("Location");

    assertNotNull(sId);

    // check user with authentication
    User persisted = given().auth().basic("test", "test")
        .when().get("/users/{id}", sId)
        .then().statusCode(HttpStatus.OK.value())
        .extract().body().as(User.class);
    assertNotNull(persisted);
  }

  @Test
  public void createMeal_userForbidden() {
    Meal meal = MealBuilder.aMeal()
        .withDescription("test")
        .withCalories(500)
        .withDate(LocalDate.now())
        .withTime(LocalTime.now())
        .build();

    given()
        .auth().basic("user02", "test")
        .body(meal)
        .when().post("/users/3/meals")
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void createMeal_userCreated() {
    Meal meal = MealBuilder.aMeal()
        .withDescription("test")
        .withCalories(500)
        .withDate(LocalDate.now())
        .withTime(LocalTime.now())
        .build();

    String sId = given()
        .auth().basic("user02", "test")
        .body(meal)
        .when().post("/users/4/meals")
        .then().statusCode(HttpStatus.CREATED.value())
        .extract().header("Location");

    assertNotNull(sId);
  }

  @Test
  public void createMeal_managerForbidden() {
    Meal meal = MealBuilder.aMeal()
        .withDescription("test")
        .withCalories(500)
        .withDate(LocalDate.now())
        .withTime(LocalTime.now())
        .build();

    given()
        .auth().basic("manager", "manager")
        .body(meal)
        .when().post("/users/3/meals")
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void createMeal_adminCreated() {
    Meal meal = MealBuilder.aMeal()
        .withDescription("test")
        .withCalories(500)
        .withDate(LocalDate.now())
        .withTime(LocalTime.now())
        .build();

    String sId = given()
        .auth().basic("admin", "admin")
        .body(meal)
        .when().post("/users/4/meals")
        .then().statusCode(HttpStatus.CREATED.value())
        .extract().header("Location");

    assertNotNull(sId);
  }

  @Test
  public void updateMeal_userForbidden() {
    Meal meal = MealBuilder.aMeal()
        .withDescription("test")
        .withCalories(500)
        .withDate(LocalDate.now())
        .withTime(LocalTime.now())
        .build();

    String sId = given()
        .auth().basic("user02", "test")
        .body(meal)
        .when().post("/users/4/meals")
        .then().statusCode(HttpStatus.CREATED.value())
        .extract().header("Location");

    assertNotNull(sId);

    given()
        .auth().basic("user01", "test")
        .body(meal)
        .when().put("/users/4/meals/{mealId}", sId)
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void updateMeal_userOk() {
    Meal meal = MealBuilder.aMeal()
        .withDescription("test")
        .withCalories(500)
        .withDate(LocalDate.now())
        .withTime(LocalTime.now())
        .build();

    String sId = given()
        .auth().basic("user02", "test")
        .body(meal)
        .when().post("/users/4/meals")
        .then().statusCode(HttpStatus.CREATED.value())
        .extract().header("Location");

    assertNotNull(sId);

    meal.setDescription("updated");
    meal.setCalories(650);

    Meal updated = given()
        .auth().basic("user02", "test")
        .body(meal)
        .when().put("/users/4/meals/{mealId}", sId)
        .then().statusCode(HttpStatus.OK.value())
        .extract().body().as(Meal.class);

    assertEquals("updated", updated.getDescription());
    assertEquals(650, updated.getCalories().intValue());
  }

  @Test
  public void deleteMeal_userForbidden() {
    Meal meal = MealBuilder.aMeal()
        .withDescription("test")
        .withCalories(500)
        .withDate(LocalDate.now())
        .withTime(LocalTime.now())
        .build();

    String sId = given()
        .auth().basic("user02", "test")
        .body(meal)
        .when().post("/users/4/meals")
        .then().statusCode(HttpStatus.CREATED.value())
        .extract().header("Location");

    assertNotNull(sId);

    given()
        .auth().basic("user01", "test")
        .when().delete("/users/4/meals/{mealId}", sId)
        .then().statusCode(HttpStatus.FORBIDDEN.value());
  }
}
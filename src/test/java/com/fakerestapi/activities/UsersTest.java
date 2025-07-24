package com.fakerestapi.activities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;

public class UsersTest {

    @Test
    public void getAllUsersTest() {
        Response response = given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Users");

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();

        // Optional: Basic validations
        assertTrue(response.jsonPath().getList("").size() > 0, "User list should not be empty");
        assertNotNull(response.jsonPath().get("[0].id"), "First user's ID should not be null");
    }
    @Test
    public void createUserTest() {
        String requestBody = """
            {
              "id": 1,
              "userName": "userName1",
              "password": "password"
            }
            """;

        given()
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Users")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("userName", equalTo("userName1"))
                .body("password", equalTo("password"));
    }
    @Test
    public void getUserByIdTest() {
        given()
                .header("accept", "*/*")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Users/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }
    @Test
    public void updateUserByIdTest() {
        String requestBody = """
            {
              "id": 1,
              "userName": "UpdatedUserName",
              "password": "updatedPassword"
            }
            """;

        given()
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("https://fakerestapi.azurewebsites.net/api/v1/Users/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("userName", equalTo("UpdatedUserName"))
                .body("password", equalTo("updatedPassword"));
    }
    @Test
    public void deleteUserByIdTest() {
        given()
                .header("accept", "*/*")
                .when()
                .delete("https://fakerestapi.azurewebsites.net/api/v1/Users/1")
                .then()
                .statusCode(200); // Use 204 if the API returns No Content instead
    }
}

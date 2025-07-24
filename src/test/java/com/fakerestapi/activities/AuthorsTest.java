package com.fakerestapi.activities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class AuthorsTest {

    @Test
    public void getAllAuthorsTest() {
        Response response = given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Authors");

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();

        // Optional: Basic assertions
        assertTrue(response.jsonPath().getList("").size() > 0, "Author list should not be empty");
        assertNotNull(response.jsonPath().get("[0].id"), "First author ID should not be null");
    }
    @Test
    public void createAuthorTest() {
        String requestBody = """
            {
              "id": 1,
              "idBook": 1,
              "firstName": "John",
              "lastName": "Smith"
            }
            """;

        given()
                .header("accept", "text/plain; v=1.0")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Authors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("idBook", equalTo(1))
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Smith"));
    }
    @Test
    public void getAuthorsByBookIdTest() {
        given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Authors/authors/books/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0)) // ensure response contains authors
                .body("[0].idBook", equalTo(1)); // validate bookId is 1
    }
    public void getAuthorByIdTest() {
        given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Authors/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }
}

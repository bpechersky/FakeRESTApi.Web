package com.fakerestapi.activities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;

public class BooksTest {

    @Test
    public void getAllBooksTest() {
        Response response = given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Books");

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();

        // Optional basic validations
        assertTrue(response.jsonPath().getList("").size() > 0, "Book list should not be empty");
        assertNotNull(response.jsonPath().get("[0].id"), "First book's ID should not be null");
    }
    @Test
    public void createBookTest() {
        String requestBody = """
            {
              "id": 1,
              "title": "Title 1",
              "description": "Description 1 ",
              "pageCount": 50,
              "excerpt": "Expert",
              "publishDate": "2025-07-24T03:44:26.091Z"
            }
            """;

        given()
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Books")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("title", equalTo("Title 1"))
                .body("description", equalTo("Description 1 "))
                .body("pageCount", equalTo(50))
                .body("excerpt", equalTo("Expert"))
                .body("publishDate", equalTo("2025-07-24T03:44:26.091Z"));
    }
    @Test
    public void getBookByIdTest() {
        given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Books/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }
    @Test
    public void updateBookByIdTest() {
        String requestBody = """
            {
              "id": 1,
              "title": "Updated Title",
              "description": "Updated description",
              "pageCount": 40,
              "excerpt": "Updated excerpt",
              "publishDate": "2025-07-24T03:48:20.930Z"
            }
            """;

        given()
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("https://fakerestapi.azurewebsites.net/api/v1/Books/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("title", equalTo("Updated Title"))
                .body("description", equalTo("Updated description"))
                .body("pageCount", equalTo(40))
                .body("excerpt", equalTo("Updated excerpt"));

    }
    @Test
    public void deleteBookByIdTest() {
        given()
                .header("accept", "*/*")
                .when()
                .delete("https://fakerestapi.azurewebsites.net/api/v1/Books/1")
                .then()
                .statusCode(200); // Use 204 if the API returns No Content
    }
}

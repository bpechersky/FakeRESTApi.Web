package com.fakerestapi.activities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;

public class CoverPhotosTest {

    @Test
    public void getAllCoverPhotosTest() {
        Response response = given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/CoverPhotos");

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();

        // Optional: basic validation
        assertTrue(response.jsonPath().getList("").size() > 0, "Cover photo list should not be empty");
        assertNotNull(response.jsonPath().get("[0].id"), "First cover photo ID should not be null");
    }
    @Test
    public void createCoverPhotoTest() {
        String requestBody = """
            {
              "id": 1,
              "idBook": 1,
              "url": "www.google.com"
            }
            """;

        given()
                .header("accept", "text/plain; v=1.0")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/CoverPhotos")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("idBook", equalTo(1))
                .body("url", equalTo("www.google.com"));
    }
    @Test
    public void getCoverPhotosByBookIdTest() {
        given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/CoverPhotos/books/covers/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].idBook", equalTo(1));
    }
    @Test
    public void getCoverPhotoByIdTest() {
        given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/CoverPhotos/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }
    @Test
    public void updateCoverPhotoByIdTest() {
        String requestBody = """
            {
              "id": 1,
              "idBook": 1,
              "url": "www.updatedUrl.com"
            }
            """;

        given()
                .header("accept", "text/plain; v=1.0")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("https://fakerestapi.azurewebsites.net/api/v1/CoverPhotos/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("idBook", equalTo(1))
                .body("url", equalTo("www.updatedUrl.com"));
    }
    @Test
    public void deleteCoverPhotoByIdTest() {
        given()
                .header("accept", "*/*")
                .when()
                .delete("https://fakerestapi.azurewebsites.net/api/v1/CoverPhotos/1")
                .then()
                .statusCode(200); // or use 204 if API returns No Content
    }
}

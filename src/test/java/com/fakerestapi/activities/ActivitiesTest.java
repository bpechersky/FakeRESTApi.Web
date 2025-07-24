package com.fakerestapi.activities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ActivitiesTest {

    @Test
    public void validateActivitiesResponse() {
        Response response = RestAssured
                .given()
                .header("accept", "text/plain; v=1.0")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        List<Activity> activities = response.jsonPath().getList("", Activity.class);
        Assert.assertEquals(activities.size(), 30, "Expected 30 activities");

        Assert.assertEquals(activities.get(0).getId(), 1);
        Assert.assertEquals(activities.get(0).getTitle(), "Activity 1");
        Assert.assertFalse(activities.get(0).isCompleted());

        Assert.assertEquals(activities.get(29).getId(), 30);
        Assert.assertEquals(activities.get(29).getTitle(), "Activity 30");
        Assert.assertTrue(activities.get(29).isCompleted());
    }
}

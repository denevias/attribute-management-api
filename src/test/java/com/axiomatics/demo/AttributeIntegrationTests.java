package com.axiomatics.demo;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AttributeIntegrationTests extends DemoApplicationTests {

    @Test
    public void testShouldSuccessfullyGetAttributes() {
        given()
                .when()
                .get("/api/attributes")
                .then()
                .contentType(JSON)
                .statusCode(HttpStatus.OK.value());
    }

}

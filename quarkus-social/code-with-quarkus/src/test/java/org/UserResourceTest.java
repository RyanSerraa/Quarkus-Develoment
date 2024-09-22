package org;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest 
public class UserResourceTest {

    @Test
    @DisplayName("Should create a user successfully")
    public void createUserTest() {
        // Create user request object
        var user = new CreateUserRequest();
        user.setName("Fulano");
        user.setAge(30);

        // Perform the POST request to create a user
        var response = given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("/users")
            .then()
            .extract()
            .response();

        // Print response body for debugging purposes
        System.out.println("Response body: " + response.body().asString());

        // Assert the status code is 201 (Created)
        assertEquals(201, response.statusCode());

        // Assert that the ID field is not null, indicating the user was created
        assertNotNull(response.jsonPath().getString("id"), "Expected a non-null ID for the created user");
        
        // Additional assertions to verify response content
        assertEquals("Fulano", response.jsonPath().getString("name"));
        assertEquals(30, response.jsonPath().getInt("age"));
    }
    @Test
    @DisplayName("json não validado")
    public void createUserValidationTest(){
        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        // Perform the POST request to create a user
        var response = given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("/users")
            .then()
            .extract()
            .response();

        // Print response body for debugging purposes
        System.out.println("Response body: " + response.body().asString());

        // Assert the status code is 201 (Created)
        assertEquals(422, response.statusCode());

        // Assert that the ID field is not null, indicating the user was created
       
    }
}

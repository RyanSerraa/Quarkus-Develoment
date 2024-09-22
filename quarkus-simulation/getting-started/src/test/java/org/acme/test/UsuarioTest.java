package org.acme.test;
import org.acme.service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class UsuarioTest {
        @Test
        @DisplayName("  Should create user sucess ")
        public void testPostUser() {
            var createUser = new UserService();
            createUser.setEmail("rserr7a@gmail.com");
            createUser.setPassword("wl45");
            createUser.setUsername("Ryan");
            var response=given()
            .contentType(ContentType.JSON)
            .when()
            .body(createUser)
            .post("/usuarios")
            .then()
            .extract()
            .response();
            assertEquals(201, response.statusCode());    
            assertEquals(createUser.getEmail(), response.jsonPath().getString("email"));
            assertEquals(createUser.getPassword(), response.jsonPath().getString("password"));
            assertEquals(createUser.getUsername(), response.jsonPath().getString("username"));
                       
            }

        @Test
        @DisplayName("  Should create user sucess ")
        public void testGetUser() {
            var createUser = new UserService();
            createUser.setEmail("rserr7a@gmail.com");
            createUser.setPassword("wl45");
            createUser.setUsername("Ryan");
            var response=given()
            .contentType(ContentType.JSON)
            .when()
            .body(createUser)
            .get("/usuarios")
            .then()
            .extract()
            .response();
            assertEquals(200, response.statusCode());    
            }
            @Test
            @DisplayName("  Should create user sucess ")
            public void testDeleteUser() {
                var createUser = new UserService();
                createUser.setEmail("rserr77a@gmail.com");
                createUser.setPassword("wl45");
                createUser.setUsername("Ryan");
                var response=given()
                .contentType(ContentType.JSON)
                .when()
                .body(createUser)
                .post("/usuarios")
                .then()
                .extract()
                .response();
                assertEquals(201, response.statusCode());    
                assertEquals(createUser.getEmail(), response.jsonPath().getString("email"));
                assertEquals(createUser.getPassword(), response.jsonPath().getString("password"));
                assertEquals(createUser.getUsername(), response.jsonPath().getString("username"));

                var responseDelete=given()
                .contentType(ContentType.JSON)
                .when()
                .body(createUser)
                .pathParam("id", 2)
                .delete("/usuarios/{id}")
                .then()
                .extract()
                .response();
                assertEquals(204, responseDelete.statusCode());
                           
                }
}

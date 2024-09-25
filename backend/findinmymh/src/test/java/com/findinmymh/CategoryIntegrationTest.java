package com.findinmymh;

import com.findinmymh.entities.Category;
import com.findinmymh.repository.CategoryRepository;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15"
    );

    @LocalServerPort
    private Integer port;


    @BeforeAll
    static void beforeAll() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = port;
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        //RestAssured.port = port;
        categoryRepository.deleteAll();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldReturnCategory() throws Exception {
        Category category = new Category("general");
        categoryRepository.save(category);
        RestAssured.when()
                .get("/api/category")
                .then()
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("message", Matchers.equalTo("Categories found"))
                .body("error", Matchers.nullValue());
    }

    @Test
    void shouldCreateCategory() throws Exception {
        String requestbosy = """
                {
                    "name": "general"
                }
                """; //JSON string
        RestAssured.given()
                .body(requestbosy)
                .contentType("application/json")
                .when()
                .post("/api/category")
                .then()
                .statusCode(201)
                .body("success", Matchers.equalTo(true))
                .body("message", Matchers.equalTo("Category created successfully"))
                .body("error", Matchers.nullValue());
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        Category category = new Category("general");
        categoryRepository.save(category);
        String requestbosy = """
                {
                     "name": "painting"
                 }
                """; //JSON string
        RestAssured.given()
                .body(requestbosy)
                .contentType("application/json")
                .when()
                .patch("/api/category/general")
                .then()
                .statusCode(201)
                .body("success", Matchers.equalTo(true))
                .body("message", Matchers.equalTo("Category updated successfully"))
                .body("error", Matchers.nullValue());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        Category category = new Category("general");
        categoryRepository.save(category);
        String requestbosy = """
                {
                     "name": "general"
                 }
                """; //JSON string
        RestAssured.given()
                .body(requestbosy)
                .contentType("application/json")
                .when()
                .delete("/api/category")
                .then()
                .statusCode(201)
                .body("success", Matchers.equalTo(true))
                .body("message", Matchers.equalTo("Category deleted successfully"))
                .body("error", Matchers.nullValue());
    }

}

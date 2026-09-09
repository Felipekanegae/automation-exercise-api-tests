package tests;

import config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductsTest extends ApiConfig {



    @Test
    public void getAllProductsList() {
        Response response =
                given()
                        .when()
                        .get("/productsList");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();
        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);
        assertEquals(200, jsonPath.getInt("responseCode"));
        assertFalse(jsonPath.getList("products").isEmpty());
        assertEquals(1, jsonPath.getInt("products[0].id"));
    }
}
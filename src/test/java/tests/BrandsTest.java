package tests;


import config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class BrandsTest extends ApiConfig {

    @Test
    public void getAllBrandList() {
        Response response =
                given()
                        .when()
                        .get("/brandsList");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();
        String jsonBody = responseBody.substring(responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertFalse(jsonPath.getList("brands").isEmpty());

    }

    @Test
    public void putToAllBrandsList() {
        Response response =
                given()
                        .when()
                        .put("/brandsList");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(405, jsonPath.getInt("responseCode"));
        assertEquals("This request method is not supported.", jsonPath.getString("message"));


    }

}

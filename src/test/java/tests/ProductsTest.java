package tests;

import testData.ExcelTestData;
import config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class ProductsTest extends ApiConfig {

    private final ExcelTestData testData = new ExcelTestData();

    @Test
    public void getAllProductsList() {
        Response response =
                given()
                        .when()
                        .get("/productsList");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();
        String jsonBody = responseBody.substring(responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertFalse(jsonPath.getList("products").isEmpty());

    }

    @Test
    public void postToAllProductsList() {
        Response response =
                given()
                        .when()
                        .post("/productsList");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(405, jsonPath.getInt("responseCode"));
        assertEquals("This request method is not supported.",
                jsonPath.getString("message"));

    }

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

    @Test
    public void searchProduct() {
        Response response =
                given()
                        .formParam("search_product", "top")
                        .when()
                        .post("/searchProduct");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertFalse(jsonPath.getList("products").isEmpty());

        List<String> productNames = jsonPath.getList("products.name");
        assertTrue(productNames.stream().anyMatch(name -> name.toLowerCase().contains("top")));

    }

    @Test
    public void searchProductWithoutSearchProductParameter() {
        Response response =
                given()
                        .when()
                        .post("/searchProduct");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(400, jsonPath.getInt("responseCode"));
        assertEquals("Bad request, search_product parameter is missing in POST request.",
                jsonPath.getString("message"));

    }

    @Test
    public void loginWithValidDetails() {

        testData.loadTestData(
                "src/test/resources/testData/apiTestData.xlsx", "Login", "CT007");

        Response response =
                given()
                        .formParam("email", testData.getStringOf("EMAIL"))
                        .formParam("password", testData.getStringOf("PASSWORD"))
                        .when()
                        .post("/verifyLogin");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1
        );

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertEquals("User exists!", jsonPath.getString("message"));
    }

}
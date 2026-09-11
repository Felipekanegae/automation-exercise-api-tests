package tests;

import testData.ExcelTestData;
import config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends ApiConfig {

    private final ExcelTestData testData = new ExcelTestData();

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
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertEquals("User exists!", jsonPath.getString("message"));
    }

    @Test
    public void loginWithoutEmailParameter() {

        testData.loadTestData(
                "src/test/resources/testData/apiTestData.xlsx", "Login", "CT008");

        Response response =
                given()
                        .formParam("password", testData.getStringOf("PASSWORD"))
                        .when()
                        .post("/verifyLogin");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(400, jsonPath.getInt("responseCode"));
        assertEquals("Bad request, email or password parameter is missing in POST request.", jsonPath.getString("message"));

    }

    @Test
    public void deleteToVerifyLogin() {

        Response response =
                given()
                        .when()
                        .delete("/verifyLogin");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(405, jsonPath.getInt("responseCode"));
        assertEquals("This request method is not supported.", jsonPath.getString("message"));

    }

    @Test
    public void loginWithInvalidDetails() {

        testData.loadTestData(
                "src/test/resources/testData/apiTestData.xlsx", "Login", "CT010");

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
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(404, jsonPath.getInt("responseCode"));
        assertEquals("User not found!", jsonPath.getString("message"));
    }

}

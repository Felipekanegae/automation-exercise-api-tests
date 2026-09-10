package tests;

import config.ApiConfig;
import testData.ExcelTestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class UserAccountTest extends ApiConfig {

    private final ExcelTestData testData = new ExcelTestData();

    @Test
    public void createUserAccount() {

        testData.loadTestData(
                "src/test/resources/testData/apiTestData.xlsx", "Login", "CT011");

        Response response =
                given()
                        .formParam("name", testData.getStringOf("NAME"))
                        .formParam("email", testData.getStringOf("EMAIL"))
                        .formParam("password", testData.getStringOf("PASSWORD"))
                        .formParam("title", testData.getStringOf("TITLE"))
                        .formParam("birth_date", testData.getStringOf("BIRTH_DATE"))
                        .formParam("birth_month", testData.getStringOf("BIRTH_MONTH"))
                        .formParam("birth_year", testData.getStringOf("BIRTH_YEAR"))
                        .formParam("firstname", testData.getStringOf("FIRSTNAME"))
                        .formParam("lastname", testData.getStringOf("LASTNAME"))
                        .formParam("company", testData.getStringOf("COMPANY"))
                        .formParam("address1", testData.getStringOf("ADDRESS1"))
                        .formParam("address2", testData.getStringOf("ADDRESS2"))
                        .formParam("country", testData.getStringOf("COUNTRY"))
                        .formParam("zipcode", testData.getStringOf("ZIPCODE"))
                        .formParam("state", testData.getStringOf("STATE"))
                        .formParam("city", testData.getStringOf("CITY"))
                        .formParam("mobile_number", testData.getStringOf("MOBILE_NUMBER"))
                        .when()
                        .post("/createAccount");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(201, jsonPath.getInt("responseCode"));
        assertEquals("User created!", jsonPath.getString("message"));
    }


}

package tests;

import config.ApiConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import testData.ExcelTestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAccountTest extends ApiConfig {

    private final ExcelTestData testData = new ExcelTestData();

    @Order(1)
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


    @Order(2)
    @Test
    public void updateUserAccount() {

        testData.loadTestData(
                "src/test/resources/testData/apiTestData.xlsx", "Login", "CT013");

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
                        .put("/updateAccount");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertEquals("User updated!", jsonPath.getString("message"));

    }
    @Order(3)
    @Test
    public void getUserAccountDetails() {
        testData.loadTestData(
                "src/test/resources/testData/apiTestData.xlsx", "Login", "CT014");

        Response response =
                given()
                        .queryParam("email", testData.getStringOf("EMAIL"))
                        .when()
                        .get("/getUserDetailByEmail");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));

        assertEquals(testData.getStringOf("EMAIL"), jsonPath.getString("user.email"));
        assertEquals(testData.getStringOf("NAME"), jsonPath.getString("user.name"));
        assertEquals(testData.getStringOf("TITLE"), jsonPath.getString("user.title"));
        assertEquals(testData.getStringOf("BIRTH_DATE"), jsonPath.getString("user.birth_day"));
        assertEquals(testData.getStringOf("BIRTH_MONTH"), jsonPath.getString("user.birth_month"));
        assertEquals(testData.getStringOf("BIRTH_YEAR"), jsonPath.getString("user.birth_year"));
        assertEquals(testData.getStringOf("FIRSTNAME"), jsonPath.getString("user.first_name"));
        assertEquals(testData.getStringOf("LASTNAME"), jsonPath.getString("user.last_name"));
        assertEquals(testData.getStringOf("COMPANY"), jsonPath.getString("user.company"));
        assertEquals(testData.getStringOf("ADDRESS1"), jsonPath.getString("user.address1"));
        assertEquals(testData.getStringOf("ADDRESS2"), jsonPath.getString("user.address2"));
        assertEquals(testData.getStringOf("COUNTRY"), jsonPath.getString("user.country"));
        assertEquals(testData.getStringOf("ZIPCODE"), jsonPath.getString("user.zipcode"));
        assertEquals(testData.getStringOf("STATE"), jsonPath.getString("user.state"));
        assertEquals(testData.getStringOf("CITY"), jsonPath.getString("user.city"));

    }

    @Order(4)
    @Test
    public void deleteUserAccount() {
        testData.loadTestData(
                "src/test/resources/testData/apiTestData.xlsx", "Login", "CT012");

        Response response =
                given()
                        .formParam("email", testData.getStringOf("EMAIL"))
                        .formParam("password", testData.getStringOf("PASSWORD"))
                        .when()
                        .delete("/deleteAccount");

        response.then()
                .statusCode(200);

        String responseBody = response.getBody().asString();

        String jsonBody = responseBody.substring(
                responseBody.indexOf("{"),
                responseBody.lastIndexOf("}") + 1);

        JsonPath jsonPath = new JsonPath(jsonBody);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertEquals("Account deleted!", jsonPath.getString("message"));

    }


}

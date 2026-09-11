# Automation Exercise - API Test Automation

API test automation project developed using Java, REST Assured, JUnit 5, Maven, and Apache POI.

The project automates the API test scenarios provided by Automation Exercise, covering positive and negative scenarios, HTTP methods, response validation, and user account lifecycle operations.

## Technologies

- Java 11
- REST Assured
- JUnit 5
- Maven
- Apache POI
- JsonPath

## Project Structure

```text
src/test/java
├── config
│   └── ApiConfig.java
├── testData
│   └── ExcelTestData.java
└── tests
    ├── BrandsTest.java
    ├── LoginTest.java
    ├── ProductsTest.java
    └── UserAccountTest.java
```

## Test Coverage

The project contains 14 automated API test scenarios.

### Products

- Get all products list
- Validate unsupported POST request to products list
- Search for a product
- Search for a product without the required parameter

### Brands

- Get all brands list
- Validate unsupported PUT request to brands list

### Login

- Verify login with valid credentials
- Verify login without email parameter
- Validate unsupported DELETE request to verify login
- Verify login with invalid credentials

### User Account

- Create user account
- Update user account
- Get user account details by email
- Delete user account

The user account tests follow the lifecycle:

`Create → Update → Get Details → Delete`

JUnit 5 test ordering is used to ensure the account exists while the dependent operations are executed.

## Validations

The automated tests validate:

- HTTP status codes
- API response codes
- Response messages
- JSON response content
- Product and brand lists
- Product search results
- User account information

> **Note:** Some Automation Exercise endpoints return HTTP status `200` while providing the expected API result through the `responseCode` field in the response body. For this reason, the tests validate both the HTTP status and the application-level response code.

## Test Data

Test data is externalized in an Excel spreadsheet and loaded using Apache POI.

```text
src/test/resources/testData/apiTestData.xlsx
```

This keeps test data separated from the test implementation and allows different data sets to be associated with individual test cases.

## Running the Tests

### Prerequisites

- Java 11 or higher
- Maven

Clone the repository:

```bash
git clone https://github.com/Felipekanegae/automation-exercise-api-tests.git
```

Navigate to the project directory:

```bash
cd automation-exercise-api-tests
```

Run the complete test suite:

```bash
mvn clean test
```

A successful execution should run all 14 automated scenarios.

## API

The tests use the public API provided by Automation Exercise:

`https://automationexercise.com/api`

## Author

Felipe Kanegae  
QA Automation Engineer

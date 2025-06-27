package grischa.e2etests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApplicationFlowTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8082";
    }

    @Test
    public void testInsuranceApplicationCalculationFlow() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "zipcode": "10115",
                    "carType": "SUV",
                    "kilometers": 15000
                }
            """)
                .when()
                .post("http://localhost:8082/calculate")
                .then()
                .statusCode(200)
                .body("premium", notNullValue());
    }
}

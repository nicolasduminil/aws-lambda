package fr.simplex_software.aws.lambda.quarkus;

import io.quarkus.test.junit.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class HealthCheckTest
{
  @Test
  public void testHealthLiveness()
  {
    given()
      .when()
      .get("/health/live")
      .then()
      .statusCode(200)
      .body("status", is("UP"))
      .body("checks.size()", is(1))
      .body("checks.name[0]", is("Liveness health check"));
  }

  @Test
  public void testHealthReadiness()
  {
    given()
      .when()
      .get("/health/ready")
      .then()
      .statusCode(200)
      .body("status", is("UP"))
      .body("checks.size()", is(1))
      .body("checks.name[0]", is("Readiness health check"));
  }
}

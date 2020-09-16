package fr.simplex_software.aws.lambda.quarkus;

import io.quarkus.test.junit.*;
import io.restassured.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class GreetingTest
{
  @Test
  public void testJaxrs()
  {
    RestAssured.when().get("/hello").then()
      .contentType("text/plain")
      .body(equalTo("hello jaxrs"));
  }
}

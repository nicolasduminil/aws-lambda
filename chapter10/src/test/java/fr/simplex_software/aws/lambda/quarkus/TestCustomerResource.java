package fr.simplex_software.aws.lambda.quarkus;

import io.quarkus.test.junit.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class TestCustomerResource
{
  @Test
  public void testGetCustomerById()
  {
    get("/client/1").then().statusCode(200).body("firstName", equalTo("Robert"));
  }

  @Test
  public void testGetCustomersByLastName()
  {
    get("/client/customer/Smith").then().statusCode(200).body("size()", is(1))
      .extract().response().body().jsonPath().getList(".", Customer.class).get(0).getFirstName().equals("Robert");
  }

  @Test
  public void testGetCustomers()
  {
    get("/client").andReturn().then().statusCode(200).body("id", hasItems(1, 2, 3, 4)).body("firstName", hasItems("Robert", "Jane", "Fabienne", "Brigitte"));
  }

  @Test
  public void testGetCustomersAsync()
  {
    get("/client").andReturn().then().statusCode(200).body("id", hasItems(1, 2, 3, 4)).body("firstName", hasItems("Robert", "Jane", "Fabienne", "Brigitte"));
  }

  @Test
  public void testGetCustomersAsUni()
  {
    get("/client").andReturn().then().statusCode(200).body("id", hasItems(1, 2, 3, 4)).body("firstName", hasItems("Robert", "Jane", "Fabienne", "Brigitte"));
  }
}

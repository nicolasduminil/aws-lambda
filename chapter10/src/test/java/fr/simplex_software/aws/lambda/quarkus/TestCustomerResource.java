package fr.simplex_software.aws.lambda.quarkus;

import io.quarkus.test.junit.*;
import io.restassured.response.*;
import org.junit.jupiter.api.*;

import javax.inject.*;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestCustomerResource
{
  @Inject
  CustomerResource customerResource;

  @Test
  public void testGetCustomerById()
  {
    customerResource.resetCounter();
    customerResource.setFailRatio(0f);
    get("/customers/1").then().statusCode(200).body("firstName", equalTo("Robert"));
    get("/customers/1").then().statusCode(200).body("firstName", equalTo("Robert"));
    get("/customers/1/").then().statusCode(500).body(is("RuntimeException: Service failed."));
    get("/customers/1").then().statusCode(500).body(is("RuntimeException: Service failed."));
    get("/customers/1").then().statusCode(500).body(containsString("CircuitBreakerOpenException"));
  }

  @Test
  public void testGetCustomersByLastName()
  {
    get("/customers/customer/Smith").then().statusCode(200).body("size()", is(1))
      .extract().response().body().jsonPath().getList(".", Customer.class).get(0).getFirstName().equals("Robert");
  }

  @Test
  public void testGetCustomers()
  {
    customerResource.resetCounter();
    customerResource.setFailRatio(0f);
    get("/customers").then().statusCode(200).body("id", hasItems(1, 2, 3, 4)).body("firstName", hasItems("Robert", "Jane", "Fabienne", "Brigitte"));
    assertEquals(1, customerResource.getCounter().longValue());
    customerResource.resetCounter();
    customerResource.setFailRatio(1f);
    get("/customers").then().statusCode(500);
    assertEquals(5, customerResource.getCounter().longValue());
  }

  @Test
  public void testGetCustomersById2()
  {
    customerResource.setFailRatio(0f);
    get("/customers/").then().statusCode(200).body("size()", is(4));
    customerResource.setFailRatio(1f);
    get("/customers").then().statusCode(500);
  }
}

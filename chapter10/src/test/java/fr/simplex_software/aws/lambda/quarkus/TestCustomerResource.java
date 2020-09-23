package fr.simplex_software.aws.lambda.quarkus;

import io.quarkus.test.junit.*;
import io.restassured.http.*;
import org.junit.jupiter.api.*;

import javax.inject.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class TestCustomerResource
{
  @Inject
  CustomerResource customerResource;

  @Test
  public void testGetCustomerById()
  {
    get("/customers/1").then().statusCode(200).body("firstName", equalTo("Robert"));
    assertMetricValue("fr.simplex_software.aws.lambda.quarkus.CustomerResource.customerByIdCount", 1);
    get("/customers/1").then().statusCode(200).body("firstName", equalTo("Robert"));
    assertMetricValue("fr.simplex_software.aws.lambda.quarkus.CustomerResource.customerByIdCount", 2);
  }

  @Test
  public void testGetCustomersByLastName()
  {
    get("/customers/customer/Smith").then().statusCode(200).body("size()", is(1))
      .extract().response().body().jsonPath().getList(".", Customer.class).get(0).getFirstName().equals("Robert");
    assertMetricValue("fr.simplex_software.aws.lambda.quarkus.CustomerResource.customersByLastNameCount", 1);
    get("/customers/customer/Smith").then().statusCode(200).body("size()", is(1))
      .extract().response().body().jsonPath().getList(".", Customer.class).get(0).getFirstName().equals("Robert");
    assertMetricValue("fr.simplex_software.aws.lambda.quarkus.CustomerResource.customersByLastNameCount", 2);
  }

  @Test
  public void testGetCustomers()
  {
    get("/customers").andReturn().then().statusCode(200).body("id", hasItems(1, 2, 3, 4)).body("firstName", hasItems("Robert", "Jane", "Fabienne", "Brigitte"));
    assertMetricValue("fr.simplex_software.aws.lambda.quarkus.CustomerResource.customersCount", 1);
    get("/customers").andReturn().then().statusCode(200).body("id", hasItems(1, 2, 3, 4)).body("firstName", hasItems("Robert", "Jane", "Fabienne", "Brigitte"));
    assertMetricValue("fr.simplex_software.aws.lambda.quarkus.CustomerResource.customersCount", 2);
  }

  @Test
  public void testCustomersNumber()
  {
    get("/customers/count").andReturn().then().statusCode(200);
    assertMetricValue("fr.simplex_software.aws.lambda.quarkus.CustomerResource.customersNumberGauge", 4);
  }

  private void assertMetricValue(String metric, Object value)
  {
    given().header(new Header("Accept", "application/json")).get("/metrics/application").then().statusCode(200).body("'" + metric + "'", is(value));
  }
}

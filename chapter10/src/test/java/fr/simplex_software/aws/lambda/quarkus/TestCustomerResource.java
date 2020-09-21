package fr.simplex_software.aws.lambda.quarkus;

import io.quarkus.test.junit.*;
import io.restassured.response.*;
import io.smallrye.jwt.build.*;
import org.junit.jupiter.api.*;

import javax.inject.*;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class TestCustomerResource
{
  @Inject
  CustomerResource customerResource;
  //private String token = Jwt.issuer("CN=nduminil, O=SimplexSoftware, C=FR").upn("nduminil").groups(new HashSet<>(Arrays.asList("User", "Admin"))).sign();

  @Test
  public void testGetCustomerById()
  {
    //given().header("Authorization", "Bearer " + token).when().
      get("/customers/1").then().statusCode(200).body("firstName", equalTo("Robert"));
  }

  @Test
  public void testGetCustomersByLastName()
  {
    //given().header("Authorization", "Bearer " + token).when().
      get("/customers/customer/Smith").then().statusCode(200).body("size()", is(1))
      .extract().response().body().jsonPath().getList(".", Customer.class).get(0).getFirstName().equals("Robert");
  }

  @Test
  public void testGetCustomers()
  {
    Response response = given().when().get("/customers").andReturn();
    response.then().statusCode(200).body("id", hasItems(1, 2, 3, 4)).body("firstName", hasItems("Robert", "Jane", "Fabienne", "Brigitte"));
  }
}

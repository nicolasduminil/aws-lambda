package fr.simplex_software.aws.lambda.cognito.functions.test;

import org.jboss.resteasy.client.jaxrs.engines.*;
import org.jboss.resteasy.client.jaxrs.internal.*;
import org.junit.jupiter.api.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaIT
{
  private final String API_URL = ResourceBundle.getBundle("aws-lambda-url").getString("api-url");
  private final String TOKEN = ResourceBundle.getBundle("aws-lambda-token").getString("token");
  private final WebTarget webTarget = new ResteasyClientBuilderImpl().httpEngine(new URLConnectionEngine()).build().target(API_URL);

  @Test
  public void testOpen()
  {
    Response response = webTarget.path("open").request().get();
    assertNotNull(response);
    assertEquals(Response.Status.OK, response.getStatusInfo());
  }

  @Test
  public void testSecureFails()
  {
    Response response = webTarget.path("secure").request().get();
    assertNotNull(response);
    assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
  }

  @Test
  public void testSecureSucceeds()
  {
    Response response = webTarget.path("secure").request().header("Authorization", "Bearer " + TOKEN).get();
    assertNotNull(response);
    assertEquals(Response.Status.OK, response.getStatusInfo());
  }
}

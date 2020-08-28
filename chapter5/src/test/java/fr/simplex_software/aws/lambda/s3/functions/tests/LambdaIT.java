package fr.simplex_software.aws.lambda.s3.functions.tests;

import fr.simplex_software.aws.lambda.common.*;
import org.jboss.resteasy.client.jaxrs.engines.*;
import org.jboss.resteasy.client.jaxrs.internal.*;
import org.junit.jupiter.api.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaIT
{
  private final String API_URL = "http://127.0.0.1:3000/orders";
  private final WebTarget webTarget = new ResteasyClientBuilderImpl().httpEngine(new URLConnectionEngine()).build().target(API_URL);

  @Test
  public void test()
  {
    MoneyTransferUtil.getMoneyTransfers().getMoneyTransfers().forEach(moneyTransfer ->
    {
      Response response = webTarget.request().post(Entity.entity(moneyTransfer, "application/json"));
      assertNotNull(response);
      assertEquals (Response.Status.OK, response.getStatusInfo());
    });
  }
}

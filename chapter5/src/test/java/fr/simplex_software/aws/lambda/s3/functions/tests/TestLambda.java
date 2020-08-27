package fr.simplex_software.aws.lambda.s3.functions.tests;

import com.amazonaws.services.lambda.runtime.events.*;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.model.*;
import fr.simplex_software.aws.lambda.common.*;
import fr.simplex_software.aws.lambda.s3.functions.*;
import lombok.extern.slf4j.*;
import org.jboss.resteasy.client.jaxrs.*;
import org.jboss.resteasy.client.jaxrs.engines.*;
import org.jboss.resteasy.client.jaxrs.internal.*;
import org.junit.jupiter.api.*;

import javax.json.bind.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestLambda
{
  private final static ResteasyClient client = new ResteasyClientBuilderImpl().httpEngine(new URLConnectionEngine()).build();
  private static Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
  private final String API_URL = "http://127.0.0.1:3000/orders";
  private final WebTarget webTarget = new ResteasyClientBuilderImpl().httpEngine(new URLConnectionEngine()).build().target(API_URL);

  /*@Test
  public void test() throws Exception
  {
    AmazonS3 mockAmazonS3 = Mockito.mock(AmazonS3.class);
    S3Event s3Event = fixtureS3Event();
    log.info("### TestLambda.test(): s3Event {}", s3Event.getRecords().size());
    String key = s3Event.getRecords().get(0).getS3().getObject().getKey();
    String bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
    Mockito.when(mockAmazonS3.getObject(bucketName, key)).thenReturn(fixtureS3ReturnValue(key));
    FilePoller filePoller = new FilePoller(mockAmazonS3);
    assertTrue(filePoller.handleRequest(s3Event, Mockito.mock(Context.class)).getResultType().equals(ResultType.SUCCESS));
  }*/

  @Test
  public void test2()
  {
    MoneyTransferUtil.getMoneyTransfers().getMoneyTransfers().forEach(moneyTransfer ->
    {
      Response response = webTarget.request().post(Entity.entity(moneyTransfer, "application/json"));
      assertNotNull(response);
      assertEquals (Response.Status.OK, response.getStatusInfo());
    });
  }

  private S3Event fixtureS3Event() throws Exception
  {
    return jsonb.fromJson(getClass().getResourceAsStream("/s3-event.json"), S3Event.class);
  }

  private S3Object fixtureS3ReturnValue(String key)
  {
    S3Object s3Object = new S3Object();
    s3Object.setObjectContent(getClass().getResourceAsStream(String.format("/%s", key)));
    return s3Object;
  }
}

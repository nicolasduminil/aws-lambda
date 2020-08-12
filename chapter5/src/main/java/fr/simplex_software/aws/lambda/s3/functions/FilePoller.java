package fr.simplex_software.aws.lambda.s3.functions;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.*;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.jaxrs.json.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;
import org.apache.cxf.jaxrs.client.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.xml.bind.*;
import java.util.*;

public class FilePoller implements RequestHandler<S3Event, Result>
{
  private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
  /*private static Client client = ClientBuilder.newClient();
  private static WebTarget webTarget = client.target("https://sy8d5xrbye.execute-api.eu-west-3.amazonaws.com/dev");
  private Invocation.Builder builder = webTarget.request().accept("application/json");*/

  public Result handleRequest(S3Event s3Event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    List<Object> providers = new ArrayList<Object>();
    providers.add(new JacksonJsonProvider());
    S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);
    S3ObjectInputStream s3is = s3.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getUrlDecodedKey()).getObjectContent();
    MoneyTransfers moneyTransfers;
    Result result;
    try
    {
      moneyTransfers = (MoneyTransfers) JAXBContext.newInstance(MoneyTransfers.class).createUnmarshaller().unmarshal(s3is);
      moneyTransfers.getMoneyTransfers().forEach(moneyTransfer -> {
        logger.log("### We got a money transfer order: " + moneyTransfer);
        //webTarget.request().post(Entity.entity(moneyTransfer, "application/json"));
        WebClient client = WebClient.create("https://sy8d5xrbye.execute-api.eu-west-3.amazonaws.com/dev", providers);
        client = client.accept(MediaType.APPLICATION_JSON_TYPE);
        client.post(moneyTransfer);
      });
      result = new Result (ResultType.SUCCESS, "OK", "Have successfully created " + moneyTransfers.getMoneyTransfers().size() + " money transfer orders");
    }
    catch (JAXBException ex)
    {
      result = new Result (ResultType.ERROR, ex.getMessage(), "JAXB Exception");
      logger.log ("### JAXBException: " + ex + " " + ex.getMessage());
    }
    return result;
  }
}

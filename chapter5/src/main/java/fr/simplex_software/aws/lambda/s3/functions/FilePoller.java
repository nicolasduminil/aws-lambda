package fr.simplex_software.aws.lambda.s3.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.*;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;
import org.jboss.resteasy.client.jaxrs.*;
import org.jboss.resteasy.client.jaxrs.engines.*;
import org.jboss.resteasy.client.jaxrs.internal.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.xml.bind.*;
import java.util.*;

public class FilePoller implements RequestHandler<S3Event, Result>
{
  private final static ResteasyClient client = new ResteasyClientBuilderImpl().httpEngine(new URLConnectionEngine()).build();
  private final String API_URL = ResourceBundle.getBundle("aws-lambda").getString("api-url");
  private final AmazonS3 s3;
  private final WebTarget webTarget = new ResteasyClientBuilderImpl().httpEngine(new URLConnectionEngine()).build().target(API_URL);

  public FilePoller()
  {
    s3 = AmazonS3ClientBuilder.defaultClient();
  }

  public FilePoller(AmazonS3 s3)
  {
    this.s3 = s3;
  }

  public Result handleRequest(S3Event s3Event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);
    logger.log("### FilePoller.handleRequest(): We got a new event name" + record.getEventName() + " region " + record.getAwsRegion() + " source " + record.getEventSource());
    S3ObjectInputStream s3is = s3.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getUrlDecodedKey()).getObjectContent();
    MoneyTransfers moneyTransfers;
    Result result;
    try
    {
      moneyTransfers = (MoneyTransfers) JAXBContext.newInstance(MoneyTransfers.class).createUnmarshaller().unmarshal(s3is);
      moneyTransfers.getMoneyTransfers().forEach(moneyTransfer ->
      {
        logger.log("### FilePoller.handleRequest(): Sending request to " + webTarget.getUri());
        Response response = webTarget.request().post(Entity.entity(moneyTransfer, "application/json"));
        logger.log("### FilePoller.handleRequest(): We got a " + response.getStatusInfo().toString());
      });
      result = new Result(ResultType.SUCCESS, "OK", "Have successfully created " + moneyTransfers.getMoneyTransfers().size() + " money transfer orders");
    }
    catch (JAXBException ex)
    {
      result = new Result(ResultType.ERROR, ex.getMessage(), "JAXB Exception");
      logger.log("### JAXBException: " + ex + " " + ex.getMessage());
    }
    return result;
  }
}

package fr.simplex_software.aws.lambda.s3.functions;

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
import javax.xml.bind.*;

public class FilePoller implements RequestHandler<S3Event, Result>
{
  //
  // Please modify this url in order to reflect your environment
  //
  private final static String API_URL = "https://dmqyjkjrvd.execute-api.eu-west-3.amazonaws.com/dev/orders";
  private final static AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
  private final static ResteasyClient client =  new ResteasyClientBuilderImpl().httpEngine(new URLConnectionEngine()).build();
  private final static WebTarget webTarget = client.target(API_URL);

  public Result handleRequest(S3Event s3Event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);
    S3ObjectInputStream s3is = s3.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getUrlDecodedKey()).getObjectContent();
    MoneyTransfers moneyTransfers;
    Result result;
    try
    {
      moneyTransfers = (MoneyTransfers) JAXBContext.newInstance(MoneyTransfers.class).createUnmarshaller().unmarshal(s3is);
      moneyTransfers.getMoneyTransfers().forEach(moneyTransfer ->
        webTarget.request().post(Entity.entity(moneyTransfer, "application/json")));
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

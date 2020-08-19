package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.*;
import fr.simplex_software.aws.lambda.items.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;

import javax.json.bind.*;

public class SendMoneyMessageConsumer implements RequestHandler<SQSEvent, Result>
{
  private static final DynamoDBMapper mapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard().withRegion("eu-west-3").build());
  private static final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

  @Override
  public Result handleRequest(SQSEvent sqsEvent, Context context)
  {
    LambdaLogger logger = context.getLogger();
    String body = sqsEvent.getRecords().get(0).getBody();
    logger.log("### SendMoneyMessageConsumer.handleRequest(): We got an SQS message: " + body);
    MoneyTransfer moneyTransfer = jsonb.fromJson(body, MoneyTransfer.class);
    logger.log("### SendMoneyMessageConsumer.handleRequest(): Marshalling succeeded " + moneyTransfer.getReference());
    mapper.save(new MoneyTransferOrderItem(moneyTransfer));
    logger.log("### SendMoneyMessageConsumer.handleRequest(): Saved");
    return new Result(ResultType.SUCCESS, "OK", "Have successfully got an SQS message with body " + body);
  }
}

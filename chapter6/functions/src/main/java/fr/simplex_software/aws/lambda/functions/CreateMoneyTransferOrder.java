package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.*;
import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.model.*;

public class CreateMoneyTransferOrder implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
  private static final String QUEUE_NAME = "send-money-queue";
  private static final AmazonSQS sqs;
  private static final String queueUrl;

  static
  {
    sqs = AmazonSQSClientBuilder.standard().withRegion("eu-west-3").build();
    queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
  }

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context)
  {
    SendMessageRequest send_msg_request = new SendMessageRequest()
      .withQueueUrl(queueUrl)
      .withMessageBody(apiGatewayProxyRequestEvent.getBody())
      .withDelaySeconds(5);
    sqs.sendMessage(send_msg_request);
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
    apiGatewayProxyResponseEvent.setStatusCode(200);
    return apiGatewayProxyResponseEvent;
  }
}

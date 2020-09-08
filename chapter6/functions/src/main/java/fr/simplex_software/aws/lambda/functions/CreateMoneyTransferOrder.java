package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.*;
import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.model.*;
import com.okta.jwt.*;

public class CreateMoneyTransferOrder implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
  private static final String QUEUE_NAME = "send-money-queue";
  private static final AmazonSQS sqs;
  private static final String queueUrl;

  static
  {
    sqs = AmazonSQSClientBuilder.standard().withRegion("eu-central-1").build();
    queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
  }

  private AccessTokenVerifier jwtVerifier;

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context)
  {
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
    LambdaLogger logger = context.getLogger();
    logger.log("### CreateMoneyTransferOrder.handleRequest(): Have got a POST request " + apiGatewayProxyRequestEvent.getBody());
    /*try
    {
      Map<String, String> headers =
        Optional.ofNullable(apiGatewayProxyRequestEvent.getHeaders()).orElseThrow(() ->
          new JwtVerificationException("No headers found in request"));
      String authHeader = Optional.ofNullable(headers.get("Authorization")).orElseThrow(() ->
        new JwtVerificationException("Authorization header empty"));
      String token = authHeader.replaceAll("\\s*Bearer\\s*", "");
      jwtVerifier.decode(token);*/
    SendMessageRequest send_msg_request = new SendMessageRequest()
      .withQueueUrl(queueUrl)
      .withMessageBody(apiGatewayProxyRequestEvent.getBody())
      .withDelaySeconds(5);
    logger.log("### CreateMoneyTransferOrder.handleRequest(): Sending SQS message to " + queueUrl);
    sqs.sendMessage(send_msg_request);
    logger.log("### CreateMoneyTransferOrder.handleRequest(): Message sent. returning");
    apiGatewayProxyResponseEvent.setStatusCode(200);
    apiGatewayProxyResponseEvent.setBody("OK");
    /*}
    catch (JwtVerificationException ex)
    {
      logger.log("### CreateMoneyTransferOrder.handlerRequest(): Have not found headers or Authorization headers");
      apiGatewayProxyResponseEvent.setStatusCode(403);
      apiGatewayProxyResponseEvent.setBody(ex.getMessage());
    }*/
    return apiGatewayProxyResponseEvent;
  }
}

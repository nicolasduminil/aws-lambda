package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.*;

public class RemoveMoneyTransferOrder implements RequestHandler <APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("### We got an HTTP body: " + apiGatewayProxyRequestEvent.getBody());
    logger.log("### We got a PATH: " + apiGatewayProxyRequestEvent.getPath());
    logger.log("### We got an HTTP method: " + apiGatewayProxyRequestEvent.getHttpMethod());
    logger.log("### We got a resource: " + apiGatewayProxyRequestEvent.getResource());
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
    apiGatewayProxyResponseEvent.setStatusCode(200);
    return apiGatewayProxyResponseEvent;
  }
}

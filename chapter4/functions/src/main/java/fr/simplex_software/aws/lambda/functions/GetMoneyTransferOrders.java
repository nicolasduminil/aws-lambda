package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.*;

public class GetMoneyTransferOrders implements RequestHandler <APIGatewayV2WebSocketEvent, APIGatewayV2WebSocketResponse>
{
  @Override
  public APIGatewayV2WebSocketResponse handleRequest(APIGatewayV2WebSocketEvent apiGatewayV2WebSocketEvent, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("### We got an HTTP body: " + apiGatewayV2WebSocketEvent.getBody());
    logger.log("### We got a PATH: " + apiGatewayV2WebSocketEvent.getPath());
    logger.log("### We got an HTTP method: " + apiGatewayV2WebSocketEvent.getHttpMethod());
    logger.log("### We got a resource: " + apiGatewayV2WebSocketEvent.getResource());
    APIGatewayV2WebSocketResponse apiGatewayV2WebSocketResponse = new APIGatewayV2WebSocketResponse();
    apiGatewayV2WebSocketResponse.setStatusCode(200);
    return apiGatewayV2WebSocketResponse;
  }
}

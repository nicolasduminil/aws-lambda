package fr.simplex_software.aws.lambda.cognito.functions;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.*;

import java.util.*;

public class OpenLambdaFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context)
  {
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
    apiGatewayProxyResponseEvent.setStatusCode(200);
    apiGatewayProxyResponseEvent.setBody("{\"message\":\"This endpoint does not require any authentication\",\"result\":\"The request was successful\"}");
    apiGatewayProxyResponseEvent.setHeaders(Map.of("Content-Type","application/json", "Access-Control-Allow-Origin", "*",
      "Access-Control-Allow-Methods", "*", "Access-Control-Allow-Headers", "*"));
    return apiGatewayProxyResponseEvent;
  }
}

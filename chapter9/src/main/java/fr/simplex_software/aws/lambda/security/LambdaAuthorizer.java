package fr.simplex_software.aws.lambda.security;

import com.amazonaws.services.lambda.runtime.*;
import com.okta.jwt.*;
import fr.simplex_software.aws.lambda.security.policies.*;

public class LambdaAuthorizer implements RequestHandler<Token, AwsPolicy>
{
  private AccessTokenVerifier jwtVerifier;
  private Jwt jwt;

  @Override
  public AwsPolicy handleRequest(Token token, Context context)
  {
    try
    {
      jwt = jwtVerifier.decode(token.getAuthorizationToken());
    }
    catch (JwtVerificationException e)
    {
      throw new RuntimeException("Unauthorized");
    }
    String[] arnPartials = token.getMethodArn().split(":");
    String[] apiGatewayArnPartials = arnPartials[5].split("/");
    return new AwsPolicy("*", AwsPolicyDocument.getAllowAllPolicy(arnPartials[3], arnPartials[4], apiGatewayArnPartials[0], apiGatewayArnPartials[1]));
  }
}

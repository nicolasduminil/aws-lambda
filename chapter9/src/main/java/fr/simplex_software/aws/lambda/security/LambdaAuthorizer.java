package fr.simplex_software.aws.lambda.security;

import com.amazonaws.services.lambda.runtime.*;
import com.okta.jwt.*;
import fr.simplex_software.aws.lambda.security.policies.*;

import java.time.*;
import java.util.*;

public class LambdaAuthorizer implements RequestHandler<Token, AwsPolicy>
{
  private Jwt jwt;
  private static String issuer = ResourceBundle.getBundle("aws-lambda").getString("okta.oauth.issuer");
  private static String audience = ResourceBundle.getBundle("aws-lambda").getString("okta.oauth.audience");
  private static Long connectionTimeout = Long.parseLong(ResourceBundle.getBundle("aws-lambda").getString("okta.oauth.connectionTimeoutSeconds"));
  private static Long readTimeout = Long.parseLong (ResourceBundle.getBundle("aws-lambda").getString("okta.oauth.readTimeoutSeconds"));
  private AccessTokenVerifier jwtVerifier = JwtVerifiers.accessTokenVerifierBuilder()
    .setIssuer(issuer)
    .setAudience(audience)
    .setConnectionTimeout(Duration.ofSeconds(connectionTimeout))
    .setReadTimeout(Duration.ofSeconds(readTimeout))
    .build();

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

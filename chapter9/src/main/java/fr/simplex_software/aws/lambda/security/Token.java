package fr.simplex_software.aws.lambda.security;

public class Token
{
  private String type;
  private String authorizationToken;
  private String methodArn;

  public Token() {}

  /**
   * JSON input is deserialized into this object representation
   *
   * @param type               Static value - TOKEN
   * @param authorizationToken - Incoming bearer token sent by a client
   * @param methodArn          - The API Gateway method ARN that a client requested
   */
  public Token(String type, String authorizationToken, String methodArn)
  {
    this.type = type;
    this.authorizationToken = authorizationToken;
    this.methodArn = methodArn;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getAuthorizationToken()
  {
    return authorizationToken;
  }

  public void setAuthorizationToken(String authorizationToken)
  {
    this.authorizationToken = authorizationToken;
  }

  public String getMethodArn()
  {
    return methodArn;
  }

  public void setMethodArn(String methodArn)
  {
    this.methodArn = methodArn;
  }
}

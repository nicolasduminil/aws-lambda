package fr.simplex_software.aws.lambda.security.policies;

import java.util.*;

public class AwsPolicyDocument
{
  private static final String EXECUTE_API_ARN_FORMAT = "arn:aws:execute-api:%s:%s:%s/%s/%s/%s";
  //private final String version = "2012-10-17";
  private transient final String region;
  private transient final String awsAccountId;
  private transient final String restApiId;
  private transient final String stage;
  private final Statement allowStatement;
  private final List<Statement> statements;

  public AwsPolicyDocument(String region, String awsAccountId, String restApiId, String stage)
  {
    this.region = region;
    this.awsAccountId = awsAccountId;
    this.restApiId = restApiId;
    this.stage = stage;
    allowStatement = new Statement().getEmptyInvokeStatement("Allow");
    this.statements = new ArrayList<>();
    statements.add(allowStatement);
 }

  public static AwsPolicyDocument getAllowAllPolicy(String region, String awsAccountId, String restApiId, String stage)
  {
    AwsPolicyDocument policyDocument = new AwsPolicyDocument(region, awsAccountId, restApiId, stage);
    policyDocument.allowMethod(HttpMethod.GET, "*");
    return policyDocument;
  }

  /*public String getVersion()
  {
    return version;
  }*/

  public Statement[] getStatement()
  {
    return statements.toArray(new Statement[0]);
  }

  public void allowMethod(HttpMethod httpMethod, String resourcePath)
  {
    addResourceToStatement(allowStatement, httpMethod, resourcePath);
  }

  private void addResourceToStatement(Statement statement, HttpMethod httpMethod, String resourcePath)
  {
    if (resourcePath.equals("/"))
      resourcePath = "";
    String resource = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
    String method = httpMethod == HttpMethod.ALL ? "*" : httpMethod.toString();
    statement.addResource(String.format(EXECUTE_API_ARN_FORMAT, region, awsAccountId, restApiId, stage, method, resource));
  }
}

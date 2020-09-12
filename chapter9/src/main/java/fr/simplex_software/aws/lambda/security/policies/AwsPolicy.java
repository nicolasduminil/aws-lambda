package fr.simplex_software.aws.lambda.security.policies;

import java.util.*;

public class AwsPolicy
{
  private static final String VERSION = "Version";
  private static final String STATEMENT = "Statement";
  private static final String EFFECT = "Effect";
  private static final String ACTION = "Action";
  private static final String RESOURCE = "Resource";
  private static final String NOT_RESOURCE = "NotResource";
  private static final String CONDITION = "Condition";
  private Map<String, Object> policiesDocument;
  private String principalId;
  private transient AwsPolicyDocument policyDocument;

  public AwsPolicy()
  {
  }

  public AwsPolicy(String principalId, AwsPolicyDocument policyDocument)
  {
    this.principalId = principalId;
    this.policyDocument = policyDocument;
  }


  public String getPrincipalId()
  {
    return principalId;
  }

  public void setPrincipalId(String principalId)
  {
    this.principalId = principalId;
  }

  /**
   * IAM Policies use capitalized field names, but Lambda by default will serialize object members using camel case
   * <p>
   * This method implements a custom serializer to return the IAM Policy as a well-formed JSON document, with the correct field names
   *
   * @return IAM Policy as a well-formed JSON document
   */
  public Map<String, Object> getPoliciesDocument()
  {
    if (policiesDocument == null)
    {
      Map<String, Object> serializablePolicy = new HashMap<>();
      serializablePolicy.put(VERSION, policyDocument.getVersion());
      Statement[] statements = policyDocument.getStatement();
      Map<String, Object>[] serializableStatementArray = new Map[statements.length];
      for (int i = 0; i < statements.length; i++)
      {
        Map<String, Object> serializableStatement = new HashMap<>();
        Statement statement = statements[i];
        serializableStatement.put(EFFECT, statement.getEffect());
        serializableStatement.put(ACTION, statement.getAction());
        serializableStatement.put(RESOURCE, statement.getResource());
        serializableStatement.put(CONDITION, statement.getConditions());
        serializableStatementArray[i] = serializableStatement;
      }
      serializablePolicy.put(STATEMENT, serializableStatementArray);
      policiesDocument = serializablePolicy;
    }
    return policiesDocument;
  }

  public void setPoliciesDocument(Map<String, Object> policiesDocument)
  {
    this.policiesDocument = policiesDocument;
  }

  public AwsPolicyDocument getPolicyDocument()
  {
    return policyDocument;
  }

  public void setPolicyDocument(AwsPolicyDocument policyDocument)
  {
    this.policyDocument = policyDocument;
  }
}

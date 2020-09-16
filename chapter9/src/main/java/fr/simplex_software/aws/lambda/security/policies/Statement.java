package fr.simplex_software.aws.lambda.security.policies;

import java.util.*;

public class Statement
{
  private String effect;
  private String action;
  private Map<String, Map<String, Object>> conditions;
  private List<String> resourceList;

  public Statement()
  {}

  public Statement(String effect, String action, List<String> resourceList, Map<String, Map<String, Object>> conditions)
  {
    this.effect = effect;
    this.action = action;
    this.resourceList = resourceList;
    this.conditions = conditions;
  }

  public Statement getEmptyInvokeStatement(String effect)
  {
    return new Statement(effect, "execute-api:Invoke", new ArrayList<>(), new HashMap<>());
  }

  public String getEffect()
  {
    return effect;
  }

  public void setEffect(String effect)
  {
    this.effect = effect;
  }

  public String getAction()
  {
    return action;
  }

  public void setAction(String action)
  {
    this.action = action;
  }

  public String[] getResource()
  {
    return resourceList.toArray(new String[resourceList.size()]);
  }

  public void addResource(String resource)
  {
    resourceList.add(resource);
  }

  public Map<String, Map<String, Object>> getConditions()
  {
    return conditions;
  }

  public void addCondition(String operator, String key, Object value)
  {
    conditions.put(operator, Collections.singletonMap(key, value));
  }
}


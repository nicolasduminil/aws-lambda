package fr.simplex_software.aws.lambda.quarkus;

import org.eclipse.microprofile.health.*;

import javax.inject.*;

@Singleton
@Readiness
public class ReadinessHealthcheck implements HealthCheck
{
  @Override
  public HealthCheckResponse call()
  {
    return HealthCheckResponse.up("Readiness health check");
  }
}

package fr.simplex_software.aws.lambda.quarkus;

import org.eclipse.microprofile.health.*;

import javax.inject.*;

@Liveness
@Singleton
public class LivenessHealthCheck implements HealthCheck
{
  @Override
  public HealthCheckResponse call()
  {
    return HealthCheckResponse.up("Liveness health check");
  }
}

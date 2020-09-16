package fr.simplex_software.aws.lambda.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource
{
  @ConfigProperty(name = "greeting.message")
  private String message;

  @ConfigProperty(name = "greeting.suffix", defaultValue = "!")
  privateString suffix;

  @ConfigProperty(name = "greeting.name")
  private Optional<String> name;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello()
  {
    return message + " " + name.orElse("world") + suffix;
  }
}

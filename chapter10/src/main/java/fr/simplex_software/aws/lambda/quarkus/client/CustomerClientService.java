package fr.simplex_software.aws.lambda.quarkus.client;

import fr.simplex_software.aws.lambda.quarkus.*;
import org.eclipse.microprofile.rest.client.inject.*;

import javax.ws.rs.*;
import java.util.*;

@Path("/server")
@RegisterRestClient
@Produces("application/json")
public interface CustomerClientService
{
  @GET
  public List<Customer> getCustomers();
  @GET
  @Path("/{id}")
  public Customer getCustomersById(@PathParam("id") Integer id);
  @GET
  @Path("/customer/{name}")
  public List<Customer> getCustomersByLastName(@PathParam("name") String name);
}

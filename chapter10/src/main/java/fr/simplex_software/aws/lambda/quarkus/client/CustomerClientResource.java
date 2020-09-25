package fr.simplex_software.aws.lambda.quarkus.client;

import fr.simplex_software.aws.lambda.quarkus.*;
import io.smallrye.mutiny.*;
import lombok.extern.slf4j.*;
import org.eclipse.microprofile.rest.client.inject.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.concurrent.*;

@Path("/client")
@Produces("application/json")
public class CustomerClientResource
{
  @Inject
  @RestClient
  private CustomerClientService customerClientService;

  @GET
  public List<Customer> getCustomers()
  {
    return customerClientService.getCustomers();
  }

  @GET
  @Path("/{id}")
  public Customer getCustomerById(@PathParam("id") Integer id)
  {
    return customerClientService.getCustomersById(id);
  }

  @GET
  @Path("/customer/{name}")
  public List<Customer> getCustomersByLastName(@PathParam("name") String name)
  {
    return customerClientService.getCustomersByLastName(name);
  }

  @GET
  public CompletionStage<Set<Customer>> getCustomersAsync()
  {
    return customerClientService.getCustomersAsync();
  }

  @GET
  public Uni<Set<Customer>> getCustomersAsUni()
  {
    return customerClientService.getCustomersAsUni();
  }
}

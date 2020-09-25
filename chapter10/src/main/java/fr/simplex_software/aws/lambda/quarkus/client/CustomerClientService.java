package fr.simplex_software.aws.lambda.quarkus.client;

import fr.simplex_software.aws.lambda.quarkus.*;
import io.smallrye.mutiny.*;
import org.eclipse.microprofile.rest.client.inject.*;

import javax.ws.rs.*;
import java.util.*;
import java.util.concurrent.*;

@Path("/server")
@RegisterRestClient
@Produces("application/json")
public interface CustomerClientService
{
  @GET
  List<Customer> getCustomers();
  @GET
  @Path("/{id}")
  Customer getCustomersById(@PathParam("id") Integer id);
  @GET
  @Path("/customer/{name}")
  List<Customer> getCustomersByLastName(@PathParam("name") String name);
  @GET
  CompletionStage<Set<Customer>> getCustomersAsync();
  @GET
  Uni<Set<Customer>> getCustomersAsUni();
}

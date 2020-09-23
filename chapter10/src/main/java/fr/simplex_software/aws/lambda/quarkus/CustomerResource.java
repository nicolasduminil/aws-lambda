package fr.simplex_software.aws.lambda.quarkus;

import org.eclipse.microprofile.metrics.*;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource
{
  @Inject
  private CustomerService customerService;

  @GET
  @Counted(name = "customersCount", description = "How many times this method is called.")
  @Timed(name = "customersTimer", description = "How long it takes to execute the method.", unit = MetricUnits.MILLISECONDS)
  public List<Customer> getCustomers()
  {
    return customerService.getCustomers();
  }

  @Path("/{id}")
  @GET
  @Counted(name = "customerByIdCount", description = "How many times this method is called.")
  @Timed(name = "customerByIdTimer", description = "How long it takes to execute the method.", unit = MetricUnits.MILLISECONDS)
  public Response getCustomerById(@PathParam("id") int id)
  {
    return Response.ok(customerService.getCustomerById(id)).build();
  }

  @Path("/customer/{name}")
  @GET
  @Counted(name = "customersByLastNameCount", description = "How many times this method is called.")
  @Timed(name = "customersByLastNameTimer", description = "How long it takes to execute the method.", unit = MetricUnits.MILLISECONDS)
  public Response getCustomersByLastName(@PathParam("name") String lastName)
  {
    return Response.ok(customerService.getCustomersByLastName(lastName)).build();
  }

  @Path("/count")
  @GET
  @Gauge(unit = MetricUnits.NONE, name = "customersNumberGauge", description = "Number of customers in the inventory")
  public int getCustomersNumber()
  {
    return customerService.getCustomers().size();
  }
}

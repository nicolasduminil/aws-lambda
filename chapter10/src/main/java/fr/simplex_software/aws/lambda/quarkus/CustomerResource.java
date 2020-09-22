package fr.simplex_software.aws.lambda.quarkus;

import lombok.extern.slf4j.*;
import org.eclipse.microprofile.metrics.*;
import org.eclipse.microprofile.metrics.annotation.*;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.annotation.security.*;
import javax.enterprise.context.*;
import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/customers")
@RequestScoped
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
}
